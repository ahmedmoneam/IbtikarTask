package com.moneam.ibtikartask.modules.followerinformation;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.moneam.ibtikartask.model.IbtikarCommonRepository;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/28/2017.
 */

@ActivityScope
public class IFollowerInformationRepositoryImpl extends IbtikarCommonRepository implements FollowerInformationContract.IFollowerInformationRepository {
    private final TwitterSession session;

    @Inject
    public IFollowerInformationRepositoryImpl(RemoteDataSource remoteDataSource,
                                              LocalDataSource localDataSource,
                                              TwitterSession session) {
        super(remoteDataSource, localDataSource);
        this.session = session;
    }

    @Override
    public Observable<List<Tweet>> getLatestTweets(String screenName, Integer pageCount) {
        return Observable.create(e -> getRemoteDataSource()
                .getCustomTwitterApiClient(session).getStatusesService()
                .userTimeline(null, screenName,
                        pageCount, null, null, null, null, null, null)
                .enqueue(new Callback<List<Tweet>>() {
                    @Override
                    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                        if (!response.isSuccessful()) {
                            try {
                                e.onError(new Exception(new String(response.errorBody().bytes())));
                            } catch (Exception e1) {
                                Timber.e(e1, "onResponse: ");
                            }
                            return;
                        }
                        e.onNext(response.body());
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(Call<List<Tweet>> call, Throwable t) {
                        Timber.e(t, "onFailure: ");
                        e.onError(t);
                    }
                }));
    }
}
