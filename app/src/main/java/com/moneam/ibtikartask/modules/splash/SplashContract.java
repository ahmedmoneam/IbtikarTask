package com.moneam.ibtikartask.modules.splash;

import com.moneam.commonlibrary.mvp.base.BaseIModel;
import com.moneam.commonlibrary.mvp.base.BaseIPresenter;
import com.moneam.commonlibrary.mvp.base.BaseIView;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public interface SplashContract {

    interface View extends BaseIView {

        void startMainActivity();

        void startLoginActivity();
    }

    interface Model extends BaseIModel {

        boolean isUserLogin();

        int getDelayInSeconds();
    }

    interface Presenter extends BaseIPresenter {

    }
}