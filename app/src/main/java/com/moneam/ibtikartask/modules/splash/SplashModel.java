package com.moneam.ibtikartask.modules.splash;

import com.moneam.commonlibrary.mvp.base.BaseModel;
import com.moneam.ibtikartask.model.IbtikarCommonRepository;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@SplashScope
public class SplashModel extends BaseModel implements SplashContract.Model {
    private static final int DELAY_TO_START_NEXT_ACTIVITY = 500;

    @Inject
    public SplashModel(IbtikarCommonRepository commonRepository, Locale locale) {
        super(commonRepository, locale);
    }

    @Override
    public boolean isUserLogin() {
        return getLocalDataSource().isAuthUser();
    }

    @Override
    public int getDelayInSeconds() {
        return DELAY_TO_START_NEXT_ACTIVITY;
    }
}
