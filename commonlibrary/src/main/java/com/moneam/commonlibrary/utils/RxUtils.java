package com.moneam.commonlibrary.utils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 5/2/2017.
 */

public class RxUtils {
    public static void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void dispose(CompositeDisposable compositeDisposable) {
        try {
            if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
                compositeDisposable.clear();
            }
        } catch (Exception e) {
            Timber.e(e, "dispose: ");
        }
    }

    public static <T> void dispose(DisposableObserver<T> disposableObserver) {
        if (disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }
}
