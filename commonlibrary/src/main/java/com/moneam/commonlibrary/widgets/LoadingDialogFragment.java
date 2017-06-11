package com.moneam.commonlibrary.widgets;


import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.moneam.commonlibrary.R;
import com.moneam.commonlibrary.R2;
import com.moneam.commonlibrary.mvp.base.FullScreenDialogFragment;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadingDialogFragment extends FullScreenDialogFragment {
    public static final String TAG = LoadingDialogFragment.class.getSimpleName();
    @BindView(R2.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R2.id.rl_container)
    RelativeLayout rlContainer;
    @BindView(R2.id.tv_message)
    AppCompatTextView tvMessage;
    private String message;

    public LoadingDialogFragment() {
        // Required empty public constructor
    }

    public static LoadingDialogFragment newInstance() {
        return new LoadingDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_loading_dialog;
    }

    @Override
    protected boolean showAsFullScreen() {
        return true;
    }

    @Override
    public void onViewReady(View view, Bundle savedInstanceState) {
        if (tvMessage != null) {
            tvMessage.setText(message);
        }
    }

    public void show(FragmentManager manager, String tag, String message) {
        if (tvMessage != null) {
            tvMessage.setText(message);
        } else {
            this.message = message;
        }
        super.show(manager, tag);
    }

    public void show(FragmentManager manager, String tag, @StringRes int messageId) {
        if (tvMessage != null) {
            tvMessage.setText(getString(messageId));
        }
        super.show(manager, tag);
    }

    @OnClick(R2.id.rl_container)
    public void onClick() {
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
