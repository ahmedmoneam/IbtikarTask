package com.moneam.ibtikartask.modules.authentication.login;

import com.moneam.commonlibrary.mvp.base.BaseIModel;
import com.moneam.commonlibrary.mvp.base.BaseIPresenter;
import com.moneam.commonlibrary.mvp.base.BaseIView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public interface LoginContract {

    interface View extends BaseIView {

        void startMainActivity();
    }

    interface Model extends BaseIModel {

        void saveUserData(Result<TwitterSession> result);
    }

    interface Presenter extends BaseIPresenter {

        Callback<TwitterSession> twitterLoginCallback();
    }
}