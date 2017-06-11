package com.moneam.commonlibrary.mvp.model.local;

import com.google.gson.Gson;

import io.reactivex.Observable;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */

public interface LocalDataSource {
    SharedPreferencesUtils getSharedPreferences();

    Observable<String> loadStringFromAsset(String fileName);

    Gson getGson();

    void trimCache();

    void onDestroy();

    boolean isAuthUser();
}
