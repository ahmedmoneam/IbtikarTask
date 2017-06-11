package com.moneam.commonlibrary.mvp.model.remote;


import com.twitter.sdk.android.core.TwitterSession;

public interface RemoteDataSource {

    CustomTwitterApiClient getCustomTwitterApiClient(TwitterSession session);

    void onDestroy();
}
