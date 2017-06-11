package com.moneam.commonlibrary.mvp.base;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.StringRes;

import com.moneam.commonlibrary.widgets.MessageDialogFragment;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public interface BaseIView {

    void showLoading();

    void showLoading(String message);

    void showLoading(@StringRes int messageId);

    void hideLoading();

    void showMessage(String message);

    void showMessage(String message, MessageType type,
                     MessageDialogFragment.MessageActionListener listener);

    void showMessage(@StringRes int messageStringRes, MessageType type);

    void showMessage(String message, MessageType type);

    void launchActivity(Intent intent, boolean finish);

    void killMyself();

    void hideKeyboard();

    boolean checkPlayServices();

    com.tbruyelle.rxpermissions2.RxPermissions getRxPermissions();

    io.reactivex.Observable<Boolean> checkLocationPermission();

    io.reactivex.Observable<Boolean> checkLocationSettings();

    @SuppressWarnings("MissingPermission")
    io.reactivex.Observable<Location> getLastLocationUpdate();

    Observable<Location> getLocationUpdateObservable();

    void showMessage(@StringRes int messageStringRes, MessageType type, MessageDialogFragment.MessageActionListener listener);

    void subscribe(Disposable disposable);

    boolean isNetworkAvailable();
}
