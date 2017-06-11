package com.moneam.ibtikartask.modules.followerinformation;

import com.moneam.commonlibrary.mvp.base.BaseIModel;
import com.moneam.commonlibrary.mvp.base.BaseIPresenter;
import com.moneam.commonlibrary.mvp.base.BaseIView;
import com.moneam.ibtikartask.model.IIbtikarCommonRepository;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

public interface FollowerInformationContract {

    interface View extends BaseIView {

        void initTweetsList(List<Tweet> tweets);
    }

    interface Model extends BaseIModel {

        Observable<List<Tweet>> getLatestTweets();

        void setScreenName(String screenName);
    }

    interface Presenter extends BaseIPresenter {

        void updateScreenName(String screenName);
    }

    interface IFollowerInformationRepository extends IIbtikarCommonRepository {
        Observable<List<Tweet>> getLatestTweets(String screenName, Integer pageCount);
    }
}