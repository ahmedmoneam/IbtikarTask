package com.moneam.commonlibrary.mvp.base;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface BaseIPresenter {
    void onCreate();

    void onDestroy();

    void onResume();

    void onPause();

    void onStart();

    void onStop();

    void onViewReady();

    <T> Disposable subscribe(Observable<T> observable, Observer<T> observer, String message);

    <T> Disposable subscribe(Observable<T> observable, Observer<T> observer, int messageId);

    <T> Disposable subscribe(Observable<T> observable, Consumer<T> action, String message);

    <T> Disposable subscribe(Observable<T> observable, Consumer<T> action, int messageId);

    <T> Disposable subscribe(Observable<T> observable, Consumer<T> action, boolean showDialog, String message, int messageId);

    <T> Disposable subscribe(Observable<T> observable, Consumer<T> action, boolean showDialog);

    <T> Disposable subscribe(Observable<T> observable, Consumer<T> nextAction,
                             Consumer<Throwable> errorAction, boolean showDialog, String message, int messageId);

    <T> Disposable subscribe(Observable<T> observable, Observer<T> observer, boolean showDialog, String message, int messageId);

    <T> Disposable subscribe(Observable<T> observable, Observer<T> observer,
                             boolean showDialog);

    void addSubscription(Disposable subscription);

    void unSubscribe();

    void onSignOutPress();

    void initSideMenu();
}
