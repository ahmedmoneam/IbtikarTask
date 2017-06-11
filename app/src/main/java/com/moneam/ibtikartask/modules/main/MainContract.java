package com.moneam.ibtikartask.modules.main;

import com.moneam.commonlibrary.mvp.base.BaseIModel;
import com.moneam.commonlibrary.mvp.base.BaseIPresenter;
import com.moneam.commonlibrary.mvp.base.BaseIView;
import com.moneam.commonlibrary.mvp.model.entity.FollowersResponse;
import com.moneam.ibtikartask.model.IIbtikarCommonRepository;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public interface MainContract {

    interface View extends BaseIView {
        void updateList(List<User> users);

        void startFollowerInformationActivity(User data);

        void clearList();
    }

    interface Model extends BaseIModel {

        Observable<List<User>> getFollowers();

        Observable<List<User>> fetchFollowers();

        void clearCachedData();

        void resetCursor();
    }

    interface Presenter extends BaseIPresenter {

        void getNextFollowerPage();

        void getNextFollowerPage(Consumer<List<User>> onNext);

        void onFollowerItemClick(User data);

        void refreshFollowersList();
    }

    interface IMainRepository extends IIbtikarCommonRepository {
        Observable<FollowersResponse> getNextPageFollowers(String nextCursor);

        Observable<FollowersResponse> fetchNextPageFollowers(String nextCursor);

        void clearFollowersData();
    }
}