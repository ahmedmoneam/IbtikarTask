package com.moneam.ibtikartask.modules.main;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.base.BaseModel;
import com.twitter.sdk.android.core.models.User;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    private String nextCursor = "-1";

    @Inject
    public MainModel(MainContract.IMainRepository commonRepository, Locale locale) {
        super(commonRepository, locale);
    }

    MainContract.IMainRepository getIMainRepository() {
        return (MainContract.IMainRepository) getCommonRepository();
    }

    @Override
    public Observable<List<User>> getFollowers() {
        return getIMainRepository()
                .getNextPageFollowers(nextCursor)
                .doOnNext(followersResponse -> nextCursor = followersResponse.getNextCursorStr())
                .flatMap(followersResponse -> Observable.just(followersResponse.getUsers()));
    }

    @Override
    public void clearCachedData() {
        try {
            getIMainRepository().clearFollowersData();
            getLocalDataSource().trimCache();
        } catch (Exception e) {
            Timber.e(e, "clearCachedData: ");
        }
    }

    @Override
    public void resetCursor() {
        nextCursor = "-1";
    }
}
