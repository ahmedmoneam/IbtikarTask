package com.moneam.ibtikartask.modules.followerinformation;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.ibtikartask.di.IbtikarCommonRepositoryModule;

import dagger.Component;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

@ActivityScope
@Component(modules = {FollowerInformationModule.class, IbtikarCommonRepositoryModule.class},
        dependencies = ApplicationComponent.class)
public interface FollowerInformationComponent {
    void inject(FollowerInformationActivity activity);
}