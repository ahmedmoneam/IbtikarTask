package com.moneam.commonlibrary.mvp.base;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationRequest;
import com.moneam.commonlibrary.di.Injector;
import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.utils.ActivityUtils;
import com.moneam.commonlibrary.widgets.MessageDialogFragment;
import com.patloew.rxlocation.RxLocation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import timber.log.Timber;

import static com.moneam.commonlibrary.mvp.base.BaseActivity.LOCATION_TIMEOUT_IN_SECONDS;
import static com.moneam.commonlibrary.mvp.base.BaseActivity.SUFFICIENT_ACCURACY;
import static com.moneam.commonlibrary.utils.RxUtils.dispose;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseIView {
    protected View mRootView;
    @Inject
    protected P mPresenter;
    @Inject
    protected LocationRequest locationRequest;
    @Inject
    protected RxLocation rxLocation;
    @Inject
    protected Observable<Location> locationObservable;
    private Unbinder mUnbinder;
    private CompositeDisposable compositeDisposable;
    private RxPermissions rxPermissions;

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        compositeDisposable = new CompositeDisposable();
        super.onCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getViewLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        ComponentInject(Injector.INSTANCE.getAppComponent());
        onViewReady(mRootView, savedInstanceState);
        return mRootView;
    }

    protected abstract void onViewReady(View rootView, Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
        this.mPresenter = null;
        this.mRootView = null;
        this.mUnbinder = null;
        dispose(compositeDisposable);
        compositeDisposable = null;
    }

    protected abstract void ComponentInject(ApplicationComponent appComponent);

    @LayoutRes
    protected abstract int getViewLayoutResId();

    public void setData(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(@StringRes int messageStringRes, MessageType type,
                            MessageDialogFragment.MessageActionListener listener) {

    }

    @Override
    public void launchActivity(Intent intent, boolean finish) {
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public boolean checkPlayServices() {
        return false;
    }

    @Override
    public RxPermissions getRxPermissions() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        return rxPermissions;
    }

    @Override
    public io.reactivex.Observable<Boolean> checkLocationPermission() {
        return getRxPermissions().request(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .doOnNext(aBoolean -> {
                    if (!aBoolean) {
                        new SecurityException("Permission is Denied");
                    }
                });
    }

    @Override
    public io.reactivex.Observable<Boolean> checkLocationSettings() {
        return checkLocationPermission()
                .filter(permissionGranted -> permissionGranted)
                .flatMapSingle(new Function<Boolean, Single<Boolean>>() {
                    @Override
                    public Single<Boolean> apply(
                            @io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        return rxLocation.settings().checkAndHandleResolution(locationRequest)
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                });
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public io.reactivex.Observable<Location> getLastLocationUpdate() {
        return checkLocationSettings()
                .filter(GPSOpened -> GPSOpened)
                .flatMap(new Function<Boolean, ObservableSource<Location>>() {
                    @Override
                    public ObservableSource<Location> apply(
                            @io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        return io.reactivex.Observable
                                .concat(
                                        rxLocation.location().lastLocation().toObservable(),
                                        rxLocation.location().updates(locationRequest))
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .timeout(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .takeWhile(location -> location.getAccuracy() <= SUFFICIENT_ACCURACY)
                .debounce(3, TimeUnit.SECONDS)
                .doOnNext(location -> {
                    Timber.d("getLastLocationUpdate() called with location: " + location.toString());
                })
                .take(1)
                .onErrorResumeNext(io.reactivex.Observable.empty())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Location> getLocationUpdateObservable() {
        return checkLocationSettings()
                .filter(GPSOpened -> GPSOpened)
                .flatMap(new Function<Boolean, ObservableSource<Location>>() {
                    @Override
                    public ObservableSource<Location> apply(
                            @io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                        return locationObservable.debounce(10, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                });
    }

    @Override
    public void subscribe(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void showLoading(@StringRes int messageId) {

    }

    @Override
    public void showMessage(@StringRes int messageStringRes, MessageType type) {
        showMessage(messageStringRes, type, () -> {
        });
    }

    @Override
    public void showMessage(String message, MessageType type, MessageDialogFragment.MessageActionListener listener) {

    }

    @Override
    public void showMessage(String message, MessageType type) {

    }

    @Override
    public boolean isNetworkAvailable() {
        return ActivityUtils.isNetworkAvailable(getActivity());
    }
}
