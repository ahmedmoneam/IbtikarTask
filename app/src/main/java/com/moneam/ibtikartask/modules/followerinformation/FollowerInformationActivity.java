package com.moneam.ibtikartask.modules.followerinformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.mvp.base.BaseActivity;
import com.moneam.ibtikartask.R;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Abdelmoneam on 6/10/2017.
 */

public class FollowerInformationActivity
        extends BaseActivity<FollowerInformationPresenter>
        implements FollowerInformationContract.View {

    public static final String PARAM_SCREEN_NAME = "screen-name";
    public static final String PARAM_BACKGROUND_IMAGE_URL = "background-image-url";
    public static final String PARAM_PROFILE_IMAGE_URL = "profile-image-url";
    public static final String PARAM_NAME = "name";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_tweets)
    ListView lvTweets;
    @BindView(R.id.imv_background)
    AppCompatImageView imvBackground;
    @BindView(R.id.imv_profileImage)
    AppCompatImageView imvProfileImage;

    @Override
    protected int getContentView() {
        return R.layout.activity_follower_information;
    }

    @Override
    public void onViewReady(Bundle savedInstanceState, Intent intent) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        extractDataFromIntent(intent);
    }

    @Override
    protected void resolveDaggerDependency(ApplicationComponent appComponent) {
        DaggerFollowerInformationComponent
                .builder()
                .applicationComponent(appComponent)
                .followerInformationModule(new FollowerInformationModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void extractDataFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String screenName = extras.getString(PARAM_SCREEN_NAME);
            String name = extras.getString(PARAM_NAME);
            String backgroundImageUrl = extras.getString(PARAM_BACKGROUND_IMAGE_URL);
            String profileImageUrl = extras.getString(PARAM_PROFILE_IMAGE_URL);
            getPresenter().updateScreenName(screenName);
            initView(profileImageUrl, backgroundImageUrl);
        }
    }

    private void initView(String profileImageUrl, String backgroundImageUrl) {
        Picasso.with(this)
                .load(profileImageUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(imvProfileImage);

        Picasso.with(this)
                .load(backgroundImageUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .into(imvBackground);
    }

    @Override
    public void initTweetsList(List<Tweet> tweets) {
        final FixedTweetTimeline fixedTimeline = new FixedTweetTimeline.Builder()
                .setTweets(tweets).build();

        final TweetTimelineListAdapter adapter =
                new TweetTimelineListAdapter(FollowerInformationActivity.this, fixedTimeline);

        lvTweets.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
