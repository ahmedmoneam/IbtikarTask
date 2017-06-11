package com.moneam.ibtikartask.di;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.moneam.ibtikartask.model.IIbtikarCommonRepository;
import com.moneam.ibtikartask.model.IbtikarCommonRepository;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Abdelmoneam on 5/1/2017.
 */

@Module
public class IbtikarCommonRepositoryModule {
    private TwitterSession twitterSession;

    @ActivityScope
    @Provides
    IbtikarCommonRepository provideIbtikarCommonRepository(RemoteDataSource remoteDataSource,
                                                           LocalDataSource localDataSource) {
        return new IbtikarCommonRepository(remoteDataSource, localDataSource);
    }

    @ActivityScope
    @Provides
    IIbtikarCommonRepository provideIIbtikarCommonRepository(RemoteDataSource remoteDataSource,
                                                             LocalDataSource localDataSource) {
        return new IbtikarCommonRepository(remoteDataSource, localDataSource);
    }

    @ActivityScope
    @Provides
    TwitterSession provideTwitterSession() {
        if (twitterSession == null) {
            twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        }
        return twitterSession;
    }
}