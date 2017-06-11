package com.moneam.commonlibrary.mvp.base;

import com.google.gson.Gson;
import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;

import java.util.Locale;

import javax.inject.Inject;

@ApplicationScope
public class BaseModel<T extends ICommonRepository> implements BaseIModel {
    private T commonRepository;
    private Locale locale;

    @Inject
    public BaseModel(T commonRepository, Locale locale) {
        this.commonRepository = commonRepository;
        this.locale = locale;
    }

    public T getCommonRepository() {
        return commonRepository;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public LocalDataSource getLocalDataSource() {
        return getCommonRepository().getLocalDataSource();
    }

    @Override
    public RemoteDataSource getRemoteDataSource() {
        return getCommonRepository().getRemoteDataSource();
    }

    @Override
    public Gson getGson() {
        return getCommonRepository().getLocalDataSource().getGson();
    }

    @Override
    public void signOut() {
        getCommonRepository().getLocalDataSource().getSharedPreferences().clearData();
    }

    @Override
    public void onDestroy() {
        if (commonRepository != null) {
            commonRepository.onDestroy();
            commonRepository = null;
        }
    }

}
