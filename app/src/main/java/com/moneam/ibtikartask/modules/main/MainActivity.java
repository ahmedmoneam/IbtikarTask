package com.moneam.ibtikartask.modules.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.mvp.base.BaseActivity;
import com.moneam.commonlibrary.mvp.base.DefaultAdapter;
import com.moneam.commonlibrary.widgets.EndlessRecyclerViewScrollListener;
import com.moneam.ibtikartask.R;
import com.moneam.ibtikartask.modules.followerinformation.FollowerInformationActivity;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public class MainActivity extends BaseActivity<MainPresenter>
        implements MainContract.View, DefaultAdapter.OnRecyclerViewItemClickListener<User> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvFollowers)
    RecyclerView rvFollowers;

    private FollowersAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onViewReady(Bundle savedInstanceState, Intent intent) {
        setSupportActionBar(toolbar);

        layoutManager = new LinearLayoutManager(this);
        adapter = new FollowersAdapter(new ArrayList<>(0));
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getPresenter().getNextFollowerPage();
            }
        };

        rvFollowers.setLayoutManager(layoutManager);
        rvFollowers.setAdapter(adapter);
        rvFollowers.addOnScrollListener(endlessRecyclerViewScrollListener);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_refresh:
                getPresenter().refreshFollowersList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void resolveDaggerDependency(ApplicationComponent appComponent) {
        DaggerMainComponent
                .builder()
                .applicationComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void updateList(List<User> users) {
        adapter.addItems(users);
    }

    @Override
    public void startFollowerInformationActivity(User data) {
        Intent intent = new Intent(this, FollowerInformationActivity.class);
        intent.putExtra(FollowerInformationActivity.PARAM_SCREEN_NAME, data.screenName);
        intent.putExtra(FollowerInformationActivity.PARAM_BACKGROUND_IMAGE_URL, data.profileBackgroundImageUrl);
        intent.putExtra(FollowerInformationActivity.PARAM_PROFILE_IMAGE_URL, data.profileImageUrl);
        intent.putExtra(FollowerInformationActivity.PARAM_NAME, data.screenName);
        launchActivity(intent, false);
    }

    @Override
    public void clearList() {
        adapter.replaceItems(new ArrayList<>(0));
    }

    @Override
    public void onItemClick(View view, User data, int position) {
        getPresenter().onFollowerItemClick(data);
    }
}
