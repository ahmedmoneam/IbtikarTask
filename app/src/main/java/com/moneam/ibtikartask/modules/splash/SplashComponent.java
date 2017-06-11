package com.moneam.ibtikartask.modules.splash;

import com.moneam.commonlibrary.di.component.ApplicationComponent;

import dagger.Component;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@SplashScope
@Component(modules = SplashModule.class, dependencies = ApplicationComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);
}