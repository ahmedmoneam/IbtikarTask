package com.moneam.ibtikartask.application;

import com.moneam.commonlibrary.application.BaseApplication;
import com.moneam.commonlibrary.utils.ReleaseTree;
import com.moneam.ibtikartask.BuildConfig;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.twitter.sdk.android.core.Twitter;

import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public class Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
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

    @Override
    public String getBaseURL() {
        // pass
        return "http://google.com/";
    }
}
