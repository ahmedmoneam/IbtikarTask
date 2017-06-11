package com.moneam.commonlibrary.mvp.base;

import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;

import javax.inject.Inject;

/**
 * Created by Ahmed Abdelmoneam on 4/28/2017.
 */

public class CommonRepository implements ICommonRepository {
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    @Inject
    public CommonRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public RemoteDataSource getRemoteDataSource() {
        return remoteDataSource;
    }

    @Override
    public LocalDataSource getLocalDataSource() {
        return localDataSource;
    }

    @Override
    public void onDestroy() {

    }
}
