package com.moneam.ibtikartask.modules.main;

import android.content.Context;

import com.google.gson.Gson;
import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.model.entity.FollowersResponse;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.CustomTwitterApiClient;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.nytimes.android.external.fs2.SourcePersisterFactory;
import com.nytimes.android.external.store2.base.Persister;
import com.nytimes.android.external.store2.base.impl.BarCode;
import com.nytimes.android.external.store2.base.impl.Store;
import com.nytimes.android.external.store2.base.impl.StoreBuilder;
import com.nytimes.android.external.store2.middleware.GsonParserFactory;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okio.BufferedSource;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@Module
public class MainModule {
    private MainContract.View view;
    private Persister<BufferedSource, BarCode> persister = null;
    private Store<FollowersResponse, BarCode> followersStore;
    private CustomTwitterApiClient twitterApiClient;
    private FollowerFetcher fetcher;


    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    Persister<BufferedSource, BarCode> providePersister(Context context) {
        if (persister == null)
            try {
                persister = SourcePersisterFactory.create(context.getApplicationContext().getCacheDir());
            } catch (IOException e) {
                Timber.e(e, "providePersister: ");
            }
        return persister;
    }

    @ActivityScope
    @Provides
    FollowerFetcher provideFetcher(CustomTwitterApiClient apiClient, TwitterSession session) {
        if (fetcher == null)
            fetcher = new FollowerFetcher(apiClient, session);
        return fetcher;
    }

    @ActivityScope
    @Provides
    Store<FollowersResponse, BarCode> providePersistedFollowersStore(
            FollowerFetcher followerFetcher,
            Persister<BufferedSource, BarCode> bufferedSourceBarCodePersister,
            Gson gson) {

        if (followersStore == null) {
            followersStore =
                    StoreBuilder.<BarCode, BufferedSource, FollowersResponse>parsedWithKey()
                            .fetcher(followerFetcher)
                            .persister(bufferedSourceBarCodePersister)
                            .parser(GsonParserFactory.createSourceParser(gson, FollowersResponse.class))
                            .open();
        }
        return followersStore;
    }

    @ActivityScope
    @Provides
    CustomTwitterApiClient provideCustomTwitterApiClient(TwitterSession session) {
        if (twitterApiClient == null) {
            twitterApiClient = new CustomTwitterApiClient(session);
        }
        return twitterApiClient;
    }

    @ActivityScope
    @Provides
    MainContract.IMainRepository provideMainRepository(RemoteDataSource remoteDataSource,
                                                       LocalDataSource localDataSource,
                                                       Store<FollowersResponse, BarCode> followersStore,
                                                       TwitterSession session) {
        return new IMainRepositoryImpl(remoteDataSource, localDataSource, followersStore, session);
    }
}