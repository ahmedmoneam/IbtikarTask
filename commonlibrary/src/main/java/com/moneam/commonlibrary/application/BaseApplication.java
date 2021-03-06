package com.moneam.commonlibrary.application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.moneam.commonlibrary.BuildConfig;
import com.moneam.commonlibrary.di.Injector;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Ahmed Abdelmoneam on 4/17/2017.
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
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

    public void enabledStrictMode() {
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
