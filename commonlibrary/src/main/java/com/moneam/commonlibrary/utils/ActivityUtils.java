package com.moneam.commonlibrary.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.moneam.commonlibrary.R;
import com.moneam.commonlibrary.mvp.base.MessageType;
import com.moneam.commonlibrary.widgets.LoadingDialogFragment;
import com.moneam.commonlibrary.widgets.MessageDialogFragment;
import com.tapadoo.alerter.Alerter;

import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 11/17/2016.
 */

public class ActivityUtils {
    private static Toast mToast;
    private final Activity mActivity;
    private final FragmentManager fragmentManager;
    private Snackbar snackbar;
    private ProgressDialog mProgressDialog;
    private LoadingDialogFragment loadingDialogFragment;
    private MessageDialogFragment messageDialogFragment;

    public ActivityUtils(@NonNull AppCompatActivity activity) {
        this(activity, activity.getSupportFragmentManager());
    }

    public ActivityUtils(@NonNull Activity activity, @NonNull FragmentManager fragmentManager) {
        this.mActivity = activity;
        this.fragmentManager = fragmentManager;
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean netState = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        netState = true;
                        break;
                    }
                }
            }
        }
        return netState;
    }

    public void showSnakeBar(@StringRes int resId) {
        snackbar.setText(resId);
        View snackBarView = snackbar.getView();
        snackBarView.announceForAccessibility(mActivity.getString(resId));
        snackbar.setAction(null, null);
        snackbar.show();
    }

    public void showSnakeBar(@StringRes int textResId, @StringRes int actionResId, View.OnClickListener listener) {
        snackbar.setText(textResId);
        View snackBarView = snackbar.getView();
        snackBarView.announceForAccessibility(mActivity.getString(textResId));
        snackbar.setAction(actionResId, listener);
        snackbar.show();
    }

    public void showSnakeBar(@StringRes int textResId, String actionText, View.OnClickListener listener) {
        snackbar.setText(textResId);
        View snackBarView = snackbar.getView();
        snackBarView.announceForAccessibility(mActivity.getString(textResId));
        snackbar.setAction(actionText, listener);
        snackbar.show();
    }

    public void showSnakeBar(String message) {
        snackbar.setText(message);
        View snackBarView = snackbar.getView();
        snackBarView.announceForAccessibility(message);
        snackbar.setAction(null, null);
        snackbar.show();
    }

    public void setSnackBar(Snackbar snackbar) {
        this.snackbar = snackbar;
    }

    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mActivity);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void startActivity(Intent intent, boolean finish) {
        mActivity.startActivity(intent);
        if (finish) {
            ((Activity) mActivity).finish();
        }
    }

    public void makeText(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(mActivity, string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }

    public void showLoading(String message) {
        loadingDialogFragment =
                (LoadingDialogFragment) fragmentManager.findFragmentByTag(LoadingDialogFragment.TAG);

        if (loadingDialogFragment == null)
            loadingDialogFragment = LoadingDialogFragment.newInstance();

        if (!loadingDialogFragment.isVisible() && !loadingDialogFragment.isAdded())
            loadingDialogFragment.show(fragmentManager, LoadingDialogFragment.TAG, message);
    }

    public void showLoading(@StringRes int messageId) {
        showLoading(mActivity.getString(messageId));
    }

    public void hideLoading() {
        try {
            if (loadingDialogFragment != null && loadingDialogFragment.isVisible()) {
                loadingDialogFragment.dismiss();
            }
        } catch (Exception e) {
            Timber.e(e, "hideLoading:");
        }

    }

    public void showMessage(String message) {
        Alerter.create(mActivity).setText(message).show();
    }

    public void showMessage(@StringRes int messageStringRes, MessageType type,
                            MessageDialogFragment.MessageActionListener listener) {
        showMessage(mActivity.getString(messageStringRes), type, listener);
    }

    public void showMessage(String message, MessageType type,
                            MessageDialogFragment.MessageActionListener listener) {
        messageDialogFragment =
                (MessageDialogFragment) fragmentManager.findFragmentByTag(MessageDialogFragment.TAG);

        if (messageDialogFragment == null) {
            messageDialogFragment = MessageDialogFragment.newInstance(message, type, listener);
        }
        switch (type) {
            case Error:
                Alerter.create(mActivity)
                        .setIcon(R.drawable.ic_close_circle_outline)
                        .setTitle("Error")
                        .setText(message)
                        .setOnHideListener(() -> {
                            if (listener != null) {
                                listener.OnActionClick();
                            }
                        })
                        .show();
                break;
            case Done:
                messageDialogFragment.setMessage(message);
                messageDialogFragment.setMessageType(type);
                if (!messageDialogFragment.isVisible()) {
                    messageDialogFragment.show(fragmentManager,
                            MessageDialogFragment.TAG);
                }
                break;
            case Info:
                Alerter.create(mActivity)
                        .setIcon(R.drawable.ic_alert_circle_outline)
                        .setTitle("Info")
                        .setText(message)
                        .show();
                break;
            case Warning:
                Alerter.create(mActivity)
                        .setIcon(R.drawable.ic_alert_outline)
//                        .setBackgroundColor(R.color.warning)
                        .setTitle("Warning")
                        .setText(message)
                        .show();
                break;
        }
    }

    public void showMessage(@StringRes int messageStringRes, MessageType type) {
        showMessage(messageStringRes, type, () -> {
        });
    }

    public void showMessage(String message, MessageType type) {
        showMessage(message, type, () -> {
        });
    }
}
