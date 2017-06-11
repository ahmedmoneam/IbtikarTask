package com.moneam.ibtikartask.modules.splash;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@Module
public class SplashModule {
    private SplashContract.View view;

    public SplashModule(SplashContract.View view) {
        this.view = view;
    }

    @SplashScope
    @Provides
    SplashContract.View provideSplashView() {
        return this.view;
    }

    @SplashScope
    @Provides
    SplashContract.Model provideSplashModel(SplashModel model) {
        return model;
    }
}