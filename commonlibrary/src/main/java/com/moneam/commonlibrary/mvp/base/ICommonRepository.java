package com.moneam.commonlibrary.mvp.base;

import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;

/**
 * Created by Ahmed Abdelmoneam on 4/28/2017.
 */

public interface ICommonRepository {
    RemoteDataSource getRemoteDataSource();

    LocalDataSource getLocalDataSource();

    void onDestroy();
}
