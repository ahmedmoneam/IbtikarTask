package com.moneam.ibtikartask.modules.followerinformation;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.twitter.sdk.android.core.TwitterSession;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

@Module
public class FollowerInformationModule {
    private FollowerInformationContract.View view;

    public FollowerInformationModule(FollowerInformationContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FollowerInformationContract.View provideFollowerInformationView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FollowerInformationContract.Model provideFollowerInformationModel(FollowerInformationModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    FollowerInformationContract.IFollowerInformationRepository provideFollowerInformationRepository(
            RemoteDataSource remoteDataSource,
            LocalDataSource localDataSource,
            TwitterSession session) {
        return new IFollowerInformationRepositoryImpl(
                remoteDataSource,
                localDataSource,
                session);
    }

}