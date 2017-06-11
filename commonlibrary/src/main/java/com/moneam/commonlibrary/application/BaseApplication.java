package com.moneam.commonlibrary.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.moneam.commonlibrary.BuildConfig;
import com.moneam.commonlibrary.di.Injector;
import com.moneam.commonlibrary.utils.ReleaseTree;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/17/2017.
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                public void log(int priority, String tag, String message, Throwable t) {
                    Logger.log(priority, tag, message, t);
                }
            });
        } else {
            Timber.plant(new ReleaseTree());
        }
        initializeApplicationComponent();
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
            enabledStrictMode();
        }
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }

    private void initializeApplicationComponent() {
        Injector.INSTANCE.initializeAppComponent(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public abstract String getBaseURL();
}
