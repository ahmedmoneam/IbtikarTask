package com.moneam.commonlibrary.mvp.model.remote;

import com.moneam.commonlibrary.mvp.model.remote.api.FollowerApi;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Ahmed Abdelmoneam on 6/7/2017.
 */

public class CustomTwitterApiClient extends TwitterApiClient {
    public CustomTwitterApiClient(TwitterSession session) {
        super(session);
    }

    public FollowerApi getFollowerApi() {
        return getService(FollowerApi.class);
    }
}
