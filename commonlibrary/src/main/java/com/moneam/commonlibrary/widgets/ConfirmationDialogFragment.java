package com.moneam.commonlibrary.widgets;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.moneam.commonlibrary.R;
import com.moneam.commonlibrary.R2;
import com.moneam.commonlibrary.mvp.base.FullScreenDialogFragment;
import com.moneam.commonlibrary.utils.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmationDialogFragment extends FullScreenDialogFragment {
    public static final String TAG = ConfirmationDialogFragment.class.getSimpleName();
    @BindView(R2.id.tv_message)
    AppCompatTextView tvMessage;
    @BindView(R2.id.btn_negativeAction)
    AppCompatButton btnNegativeAction;
    @BindView(R2.id.btn_positiveAction)
    AppCompatButton btnPositiveAction;
    private String positiveButtonText;
    private String negativeButtonText;
    private String message;
    private ConfirmationDialogActionsListener listener;

    public ConfirmationDialogFragment() {
        // Required empty public constructor
    }

    public static ConfirmationDialogFragment newInstance(String strPositiveButton,
                                                         String strNegativeButton,
                                                         String message,
                                                         ConfirmationDialogActionsListener listener) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        fragment.setPositiveButtonText(strPositiveButton);
        fragment.setNegativeButtonText(strNegativeButton);
        fragment.setMessage(message);
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_confirmation_dialog;
    }

    @Override
    protected boolean showAsFullScreen() {
        return true;
    }

    @Override
    public void onViewReady(View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    public void initView() {
        if (Preconditions.checkisNotNullOrEmpty(positiveButtonText) && btnPositiveAction != null) {
            btnPositiveAction.setText(positiveButtonText);
        }

        if (Preconditions.checkisNotNullOrEmpty(negativeButtonText) && btnNegativeAction != null) {
            btnNegativeAction.setText(negativeButtonText);
        }

        if (Preconditions.checkisNotNullOrEmpty(message) && tvMessage != null) {
            tvMessage.setText(message);
        }
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
        initView();
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
        initView();
    }

    public void setMessage(String message) {
        this.message = message;
        initView();
    }

    public void setListener(ConfirmationDialogActionsListener listener) {
        this.listener = listener;
    }

    @OnClick({R2.id.btn_negativeAction, R2.id.btn_positiveAction, R2.id.imv_close})
    public void onClick(View view) {
        if (listener == null) return;

        switch (view.getId()) {
            case R2.id.btn_negativeAction:
                dismiss();
                listener.onNegativeButtonClick(false);
                break;
            case R2.id.btn_positiveAction:
                dismiss();
                listener.onPositiveButtonClick();
                break;
            case R2.id.imv_close:
                dismiss();
                listener.onNegativeButtonClick(true);
                break;
        }
    }

    public interface ConfirmationDialogActionsListener {
        void onPositiveButtonClick();

        void onNegativeButtonClick(boolean dismissOnly);
    }
}
