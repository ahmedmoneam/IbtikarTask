package com.moneam.commonlibrary.mvp.base;

import com.google.gson.Gson;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;

public interface BaseIModel {
    void onDestroy();

    LocalDataSource getLocalDataSource();

    RemoteDataSource getRemoteDataSource();

    Gson getGson();

    void signOut();
}
