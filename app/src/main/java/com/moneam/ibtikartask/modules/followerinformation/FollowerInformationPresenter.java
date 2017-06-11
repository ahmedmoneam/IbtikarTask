package com.moneam.ibtikartask.modules.followerinformation;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.base.BasePresenter;
import com.moneam.commonlibrary.mvp.base.MessageType;
import com.moneam.ibtikartask.R;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

@ActivityScope
public class FollowerInformationPresenter
        extends BasePresenter<FollowerInformationContract.Model, FollowerInformationContract.View>
        implements FollowerInformationContract.Presenter {

    @Inject
    public FollowerInformationPresenter(FollowerInformationContract.View rootView,
                                        FollowerInformationContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void onViewReady() {
        addSubscription(getModel().getLatestTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Tweet>>() {
                    @Override
                    public void onNext(@NonNull List<Tweet> tweets) {
                        getRootView().initTweetsList(tweets);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getRootView().showMessage(R.string.error_can_not_load_data, MessageType.Error);
                        Timber.e(e, "onError:");
                    }

                    @Override
                    public void onComplete() {
                        // pass
                    }
                }));
    }

    @Override
    public void updateScreenName(String screenName) {
        getModel().setScreenName(screenName);
    }
}
