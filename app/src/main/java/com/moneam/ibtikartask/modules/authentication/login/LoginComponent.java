package com.moneam.ibtikartask.modules.authentication.login;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.ibtikartask.modules.authentication.AuthenticationScope;

import dagger.Component;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@AuthenticationScope
@Component(modules = LoginModule.class, dependencies = ApplicationComponent.class)
public interface LoginComponent {
    void
    inject(LoginActivity activity);
}