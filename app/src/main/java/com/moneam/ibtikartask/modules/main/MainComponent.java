package com.moneam.ibtikartask.modules.main;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.ibtikartask.di.IbtikarCommonRepositoryModule;

import dagger.Component;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@ActivityScope
@Component(modules = {IbtikarCommonRepositoryModule.class, MainModule.class},
        dependencies = ApplicationComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}