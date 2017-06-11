package com.moneam.ibtikartask.modules.main;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.model.entity.FollowersResponse;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.moneam.ibtikartask.model.IbtikarCommonRepository;
import com.nytimes.android.external.store2.base.impl.BarCode;
import com.nytimes.android.external.store2.base.impl.Store;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ahmed Abdelmoneam on 4/28/2017.
 */

@ActivityScope
public class IMainRepositoryImpl extends IbtikarCommonRepository implements MainContract.IMainRepository {

    private final Store<FollowersResponse, BarCode> followersStore;
    private final TwitterSession session;

    @Inject
    public IMainRepositoryImpl(RemoteDataSource remoteDataSource,
                               LocalDataSource localDataSource,
                               Store<FollowersResponse, BarCode> followersStore,
                               TwitterSession session) {
        super(remoteDataSource, localDataSource);
        this.followersStore = followersStore;
        this.session = session;
    }

    @Override
    public Observable<FollowersResponse> getNextPageFollowers(String nextCursor) {
        return followersStore
                .get(new BarCode(FollowersResponse.class.getSimpleName(), nextCursor))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<FollowersResponse> fetchNextPageFollowers(String nextCursor) {
        return followersStore
                .fetch(new BarCode(FollowersResponse.class.getSimpleName(), nextCursor));
    }

    @Override
    public void clearFollowersData() {
        followersStore.clear();
    }
}
