package com.moneam.ibtikartask.modules.authentication.login;

import com.moneam.commonlibrary.mvp.base.BaseModel;
import com.moneam.ibtikartask.model.IbtikarCommonRepository;
import com.moneam.ibtikartask.modules.authentication.AuthenticationScope;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@AuthenticationScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    public LoginModel(IbtikarCommonRepository commonRepository, Locale locale) {
        super(commonRepository, locale);
    }

    @Override
    public void saveUserData(Result<TwitterSession> result) {
        getLocalDataSource().getSharedPreferences().putUserId(result.data.getUserId());
        getLocalDataSource().getSharedPreferences().putUserName(result.data.getUserName());
        getLocalDataSource().getSharedPreferences().putUserAuthToken(result.data.getAuthToken().token);
        getLocalDataSource().getSharedPreferences().putUserAuthSecret(result.data.getAuthToken().secret);
    }
}
