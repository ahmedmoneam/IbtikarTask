package com.moneam.commonlibrary.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSourceImpl;
import com.moneam.commonlibrary.mvp.model.local.SharedPreferencesUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */
@Module
public class LocalDataSourceModule {
    @Provides
    @ApplicationScope
    LocalDataSource provideLocalStorage(Context context, Gson gson) {
        return new LocalDataSourceImpl(context, gson);
    }

    @Provides
    @ApplicationScope
    SharedPreferencesUtils provideSharedPreferencesUtils(LocalDataSource localDataSource) {
        return localDataSource.getSharedPreferences();
    }
}
