package com.moneam.commonlibrary.mvp.model.remote.api;

import com.moneam.commonlibrary.mvp.model.entity.FollowersResponse;
import com.moneam.commonlibrary.mvp.model.remote.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ahmed Abdelmoneam on 5/4/2017.
 */

public interface FollowerApi {

    @GET(API.FollowersList)
    Call<FollowersResponse> getFollowers(@Query("screen_name") String screenName,
                                         @Query("count") String count,
                                         @Query("cursor") String cursor,
                                         @Query("skip_status") Boolean skipStatus,
                                         @Query("include_user_entities") Boolean includeUserEntities);

    @GET(API.FollowersList)
    Call<ResponseBody> getFollowersRowResponse(@Query("screen_name") String screenName,
                                               @Query("count") int count,
                                               @Query("cursor") String cursor,
                                               @Query("skip_status") Boolean skipStatus,
                                               @Query("include_user_entities") Boolean includeUserEntities);

    /*@GET(API.FollowersList)
    Call<User> show(@Query("screen_name") String var,
                    @Query("skip_status") Boolean var1,
                    @Query("include_user_entities") Boolean var2,
                    @Query("count") Integer var3);

    @GET(API.FollowersList)
    Call<Object> show2(@Query("screen_name") String var,
                       @Query("skip_status") Boolean var1,
                       @Query("include_user_entities") Boolean var2,
                       @Query("count") Integer var3);*/


}
