package com.moneam.commonlibrary.mvp.model.remote;

import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

import retrofit2.Retrofit;


@ApplicationScope
public class ServiceManager implements RemoteDataSource {
    private final Retrofit mRetrofit;
    private CustomTwitterApiClient mCustomTwitterApiClient;

    @Inject
    public ServiceManager(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    @Override
    public CustomTwitterApiClient getCustomTwitterApiClient(TwitterSession session) {
        if (mCustomTwitterApiClient == null) {
            mCustomTwitterApiClient = new CustomTwitterApiClient(session);
        }
        return mCustomTwitterApiClient;
    }

    @Override
    public void onDestroy() {

    }
}
