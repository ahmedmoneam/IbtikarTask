package com.moneam.ibtikartask.modules.authentication.login;

import com.moneam.ibtikartask.modules.authentication.AuthenticationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@Module
public class LoginModule {
    private LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @AuthenticationScope
    @Provides
    LoginContract.View provideLoginView() {
        return this.view;
    }

    @AuthenticationScope
    @Provides
    LoginContract.Model provideLoginModel(LoginModel model) {
        return model;
    }
}