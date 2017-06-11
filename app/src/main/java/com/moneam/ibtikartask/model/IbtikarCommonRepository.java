package com.moneam.ibtikartask.model;

import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;

import javax.inject.Inject;

/**
 * Created by Ahmed Abdelmoneam on 4/28/2017.
 */

public class IbtikarCommonRepository extends
        com.moneam.commonlibrary.mvp.base.CommonRepository implements IIbtikarCommonRepository {

    @Inject
    public IbtikarCommonRepository(RemoteDataSource remoteDataSource,
                                   LocalDataSource localDataSource) {
        super(remoteDataSource, localDataSource);
    }
}
