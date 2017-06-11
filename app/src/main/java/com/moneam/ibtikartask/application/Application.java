package com.moneam.ibtikartask.application;

import com.moneam.commonlibrary.application.BaseApplication;
import com.twitter.sdk.android.core.Twitter;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public class Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
    }

    @Override
    public String getBaseURL() {
        // pass
        return "http://google.com/";
    }
}
