package com.moneam.commonlibrary.mvp.base;


import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.moneam.commonlibrary.utils.Preconditions;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import timber.log.Timber;

public abstract class BasePresenter<M extends BaseIModel, V extends BaseIView> implements BaseIPresenter {
    protected CompositeDisposable compositeDisposable;
    protected M mModel;
    protected V mRootView;

    public BasePresenter(V rootView, M model) {
        this.mModel = model;
        this.mRootView = rootView;
        onCreate();
    }

    public BasePresenter(V rootView) {
        this(rootView, null);
    }

    public BasePresenter() {
        this(null, null);
    }

    @CallSuper
    @Override
    public void onCreate() {
        compositeDisposable = new CompositeDisposable();
    }

    @CallSuper
    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
        unSubscribe();
        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
        this.mRootView = null;
        this.compositeDisposable = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public abstract void onViewReady();

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Observer<T> observer, String message) {
        return subscribe(observable, observer, true, message, 0);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Observer<T> observer, int messageId) {
        return subscribe(observable, observer, true, "", messageId);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Consumer<T> action, String message) {
        return subscribe(observable, action, true, message, 0);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Consumer<T> action, int messageId) {
        return subscribe(observable, action, true, "", messageId);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Consumer<T> action,
                                    boolean showDialog, String message, int messageId) {
        return subscribe(observable, new Observer<T>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(T t) {
                try {
                    action.accept(t);
                } catch (Exception e) {
                    Timber.e(e, "onNext: ");
                }
            }
        }, showDialog, message, messageId);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Consumer<T> action,
                                    boolean showDialog) {
        return subscribe(observable, action, showDialog, "", 0);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable,
                                    Consumer<T> nextAction,
                                    Consumer<Throwable> errorAction,
                                    boolean showDialog,
                                    String message,
                                    int messageId) {
        return subscribe(observable, new Observer<T>() {
            @Override
            public void onError(Throwable e) {
                try {
                    errorAction.accept(e);
                } catch (Exception e1) {
                    Timber.e(e1, "onError: ");
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(T t) {
                try {
                    nextAction.accept(t);
                } catch (Exception e) {
                    Timber.e(e, "onError: ");
                }
            }
        }, showDialog, message, messageId);
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Observer<T> observer,
                                    boolean showDialog, String message, int messageId) {
        Disposable disposable = observable
                .compose(getLoadingTransformer(showDialog, message, messageId))
                .subscribeWith(getObserver(observer));
        compositeDisposable.add(disposable);
        return disposable;
    }

    @Override
    public <T> Disposable subscribe(Observable<T> observable, Observer<T> observer,
                                    boolean showDialog) {
        return subscribe(observable, observer, showDialog, "", 0);
    }

    @NonNull
    public <T> DisposableObserver<T> getObserver(@NonNull final Observer<T> observer) {
        return new DisposableObserver<T>() {

            @Override
            public void onComplete() {
                Timber.d("onCompleted Thread: %s", Thread.currentThread().getName());
                observer.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "onError");
                Timber.d("onError Thread: %s", Thread.currentThread().getName());

                try {
                    if (e instanceof HttpException) {
                        HttpException httpException = (HttpException) e;
                        String errorBody = httpException.response().errorBody().string();
                        // TODO: 6/7/2017 handel general network error
                        Timber.e(errorBody, "errorBody");
                    }

                } catch (IOException | JsonSyntaxException e1) {
                    Timber.e(e1, "onError");
                } catch (Exception e1) {
                    Timber.e(e1, "onError");
                }
            }

            @Override
            public void onNext(T t) {
                if (t != null) Timber.d("onNext: %s", t.toString());
                Timber.d("onNext Thread: %s", Thread.currentThread().getName());
                observer.onNext(t);
            }
        };
    }

    @NonNull
    public <T> ObservableTransformer<T, T> getLoadingTransformer(boolean showDialog, String message, int messageId) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.isNetworkAvailable();
                    Timber.d("doOnSubscribe Thread: %s", Thread.currentThread().getName());
                    if (showDialog) {
                        if (Preconditions.checkisNotNullOrEmpty(message)) {
                            mRootView.showLoading(message);
                        } else if (messageId > 0)
                            mRootView.showLoading(messageId);
                        else
                            mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    Timber.d("doAfterTerminate Thread: %s", Thread.currentThread().getName());
                    mRootView.hideLoading();
                });
    }

    @Override
    public void addSubscription(Disposable subscription) {
        compositeDisposable.add(subscription);
    }

    @CallSuper
    @Override
    public void unSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void onSignOutPress() {
        getModel().signOut();
    }

    @Override
    public void initSideMenu() {

    }

    public M getModel() {
        return mModel;
    }

    public V getRootView() {
        return mRootView;
    }
}
