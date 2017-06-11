package com.moneam.commonlibrary.utils;

import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */

public class LocalLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Timber.tag("OkHttpClient").d(message);
    }
}
