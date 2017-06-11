package com.moneam.ibtikartask.modules.authentication.login;

import com.moneam.commonlibrary.mvp.base.BasePresenter;
import com.moneam.ibtikartask.modules.authentication.AuthenticationScope;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@AuthenticationScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter(LoginContract.View rootView, LoginContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void onViewReady() {

    }

    @Override
    public Callback<TwitterSession> twitterLoginCallback() {
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                getModel().saveUserData(result);
                getRootView().startMainActivity();
            }

            @Override
            public void failure(TwitterException exception) {
                getRootView().showMessage(exception.getMessage());
                Timber.e(exception, "login failure: ");
            }
        };
    }
}
