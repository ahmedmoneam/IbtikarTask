package com.moneam.ibtikartask.modules.followerinformation;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.base.BaseModel;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

@ActivityScope
public class FollowerInformationModel extends BaseModel implements FollowerInformationContract.Model {

    private static final Integer PAGE_COUNT = 10;
    private String screenName;

    @Inject
    public FollowerInformationModel(FollowerInformationContract.IFollowerInformationRepository commonRepository, Locale locale) {
        super(commonRepository, locale);
    }

    FollowerInformationContract.IFollowerInformationRepository getInformationRepository() {
        return (FollowerInformationContract.IFollowerInformationRepository) getCommonRepository();
    }

    @Override
    public Observable<List<Tweet>> getLatestTweets() {
        return getInformationRepository().getLatestTweets(screenName, PAGE_COUNT);
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
