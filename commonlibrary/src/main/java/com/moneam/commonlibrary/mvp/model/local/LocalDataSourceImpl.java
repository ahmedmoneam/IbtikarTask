package com.moneam.commonlibrary.mvp.model.local;

import android.content.Context;

import com.google.gson.Gson;
import com.moneam.commonlibrary.di.scope.ApplicationScope;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */

@ApplicationScope
public class LocalDataSourceImpl implements LocalDataSource {

    private final SharedPreferencesUtils mSharedPreferencesUtils;
    private Context mContext;
    private Gson mGson;

    @Inject
    public LocalDataSourceImpl(Context context, Gson gson) {
        this.mContext = context;
        mGson = gson;
        mSharedPreferencesUtils = new SharedPreferencesUtils(context, gson);
    }

    @Override
    public SharedPreferencesUtils getSharedPreferences() {
        return mSharedPreferencesUtils;
    }

    @Override
    public Observable<String> loadStringFromAsset(String fileName) {
        return Observable.create(subscriber -> {
            try {
                InputStream is = mContext.getAssets().open(fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                subscriber.onNext(new String(buffer, "UTF-8"));
                subscriber.onComplete();
            } catch (IOException ex) {
                Timber.e(ex, "loadStringFromAsset: %s", "Error Load File");
                subscriber.onError(ex);
                subscriber.onComplete();
            }
        });
    }

    @Override
    public Gson getGson() {
        return mGson;
    }

    @Override
    public boolean isAuthUser() {
        return getSharedPreferences().isLoggedUser();
    }

    @Override
    public void trimCache() {
        try {
            File dir = mContext.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            Timber.e(e, "trimCache:");
        }
    }

    @Override
    public void onDestroy() {
        // FIXME: 4/25/2017
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
