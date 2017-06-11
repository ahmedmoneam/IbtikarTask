package com.moneam.commonlibrary.mvp.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;
import com.moneam.commonlibrary.R;
import com.moneam.commonlibrary.di.Injector;
import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.utils.ActivityUtils;
import com.moneam.commonlibrary.utils.events.NetworkDisableEvent;
import com.moneam.commonlibrary.widgets.LoadingDialogFragment;
import com.moneam.commonlibrary.widgets.MessageDialogFragment;
import com.patloew.rxlocation.RxLocation;
import com.tapadoo.alerter.Alerter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

import static com.moneam.commonlibrary.utils.RxUtils.dispose;

public abstract class BaseActivity<P extends BaseIPresenter>
        extends AppCompatActivity
        implements BaseIView {

    static final long LOCATION_TIMEOUT_IN_SECONDS = 12;
    //    private static final long LOCATION_UPDATE_INTERVAL = 5;
    static final float SUFFICIENT_ACCURACY = 200;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final static int REQUEST_CHECK_SETTINGS = 10000;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public NavigationView navView;
    @Inject
    protected P mPresenter;
    @Inject
    protected LocationRequest locationRequest;
    @Inject
    protected RxLocation rxLocation;
    @Inject
    protected Observable<Location> locationObservable;

    private LoadingDialogFragment loadingDialogFragment;
    private MessageDialogFragment messageDialogFragment;
    private CompositeDisposable compositeDisposable;
    private com.tbruyelle.rxpermissions2.RxPermissions rxPermissions;
    private Unbinder mUnbinder;
    private ActivityUtils activityUtils;
    private View headerView;
    private AppCompatTextView tvFullName;
    private AppCompatTextView tvEmail;
    private Alerter alerter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mUnbinder = ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        resolveDaggerDependency(Injector.INSTANCE.getAppComponent());
        EventBus.getDefault().register(this);
        rxPermissions = new com.tbruyelle.rxpermissions2.RxPermissions(this);
        compositeDisposable = new CompositeDisposable();
        onViewReady(savedInstanceState, getIntent());
        mPresenter.onViewReady();
        alerter = Alerter.create(this);
    }

    protected NavigationView getNavigationView() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        this.mPresenter = null;
        this.mUnbinder = null;
        dispose(compositeDisposable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showLoading() {
        activityUtils.showLoading(R.string.label_loading);
    }

    @Override
    public void showLoading(String message) {
        activityUtils.showLoading(message);
    }

    @Override
    public void showLoading(@StringRes int messageId) {
        activityUtils.showLoading(messageId);
    }

    @Override
    public void hideLoading() {
        activityUtils.hideLoading();
    }

    @Override
    public void showMessage(String message) {
        activityUtils.showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int messageStringRes, MessageType type,
                            MessageDialogFragment.MessageActionListener listener) {
        activityUtils.showMessage(messageStringRes, type, listener);
    }

    @Override
    public void showMessage(String message, MessageType type,
                            MessageDialogFragment.MessageActionListener listener) {
        activityUtils.showMessage(message, type, listener);
    }

    @Override
    public void showMessage(@StringRes int messageStringRes, MessageType type) {
        activityUtils.showMessage(messageStringRes, type, () -> {
        });
    }

    @Override
    public void showMessage(String message, MessageType type) {
        activityUtils.showMessage(message, type, () -> {
        });
    }

    @Override
    public void launchActivity(Intent intent, boolean finish) {
        startActivity(intent);
        if (finish) {
            killMyself();
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    @Subscribe
    public void onNetworkDisabled(NetworkDisableEvent networkDisableEvent) {
        // TODO: 6/7/2017 handel network disable
        showMessage(networkDisableEvent.getObject());
    }

    public ActivityUtils getActivityUtils() {
        return activityUtils;
    }

    public void fullScreenCall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public RxPermissions getRxPermissions() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
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
                .debounce(1, TimeUnit.SECONDS)
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
                        return locationObservable
//                                .debounce(10, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                });
    }

    @Override
    public void subscribe(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public boolean isNetworkAvailable() {
        return ActivityUtils.isNetworkAvailable(this);
    }

    @LayoutRes
    protected abstract int getContentView();

    public abstract void onViewReady(Bundle savedInstanceState, Intent intent);

    protected abstract void resolveDaggerDependency(ApplicationComponent appComponent);

}
