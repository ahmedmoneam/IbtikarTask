package com.moneam.commonlibrary.mvp.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed Abdelmoneam on 6/8/2017.
 */

public class FollowersResponse {

    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("next_cursor_str")
    @Expose
    private String nextCursorStr;
    @SerializedName("previous_cursor_str")
    @Expose
    private String previousCursorStr;

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public String getNextCursorStr() {
        return nextCursorStr;
    }

    public String getPreviousCursorStr() {
        return previousCursorStr;
    }
}
