package com.moneam.ibtikartask.modules.main;

import android.view.View;

import com.moneam.commonlibrary.mvp.base.BaseHolder;
import com.moneam.commonlibrary.mvp.base.DefaultAdapter;
import com.moneam.ibtikartask.R;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

public class FollowersAdapter extends DefaultAdapter<User> {

    public FollowersAdapter(List<User> items) {
        super(items);
    }

    @Override
    public BaseHolder<User> getHolder(View v) {
        return new FollowerViewHolder(v);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_follower;
    }
}
