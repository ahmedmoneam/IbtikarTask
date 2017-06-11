package com.moneam.commonlibrary.di.module;

import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.moneam.commonlibrary.mvp.model.remote.ServiceManager;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */
@Module
public class RemoteDataSourceModule {
    @Provides
    @ApplicationScope
    RemoteDataSource provideRemoteDataSource( Retrofit retrofit) {
        return new ServiceManager(retrofit);
    }
}
