package com.moneam.ibtikartask.modules.main;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.moneam.commonlibrary.mvp.base.BaseHolder;
import com.moneam.ibtikartask.R;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import butterknife.BindView;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

public class FollowerViewHolder extends BaseHolder<User> {
    @BindView(R.id.imv_followerImage)
    AppCompatImageView imvFollowerImage;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_handel)
    AppCompatTextView tvHandel;
    @BindView(R.id.tv_bio)
    AppCompatTextView tvBio;

    public FollowerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(User data) {
        tvName.setText(data.name);
        tvHandel.setText(data.screenName);
        tvBio.setText(data.description);
        Picasso.with(itemView.getContext())
                .load(data.profileImageUrl)
                .fit()
                .into(imvFollowerImage);
    }
}
