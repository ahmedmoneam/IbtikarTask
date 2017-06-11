package com.moneam.ibtikartask.modules.main;

import com.moneam.commonlibrary.mvp.model.remote.CustomTwitterApiClient;
import com.nytimes.android.external.store2.base.Fetcher;
import com.nytimes.android.external.store2.base.impl.BarCode;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.IOException;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */
public class FollowerFetcher implements Fetcher<BufferedSource, BarCode> {
    public static final int FOLLOWERS_PAGE_COUNT = 10;
    private CustomTwitterApiClient apiClient;
    private TwitterSession session;

    FollowerFetcher(CustomTwitterApiClient apiClient, TwitterSession session) {
        this.apiClient = apiClient;
        this.session = session;
    }

    @Nonnull
    @Override
    public Observable<BufferedSource> fetch(@Nonnull BarCode barCode) {
        return Observable.create(e -> apiClient.getFollowerApi()
                .getFollowersRowResponse(session.getUserName(),
                        FOLLOWERS_PAGE_COUNT, barCode.getKey(),
                        true, false)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call,
                                           Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            try {
                                e.onError(new Exception(response.errorBody().string()));
                            } catch (IOException e1) {
                                Timber.e(e1, "onResponse: ");
                            }
                            return;
                        }
                        e.onNext(response.body().source());
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        e.onError(t);
                    }
                }));
    }
}
