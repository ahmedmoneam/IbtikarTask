package com.moneam.commonlibrary.widgets;


import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;

import com.moneam.commonlibrary.R;
import com.moneam.commonlibrary.R2;
import com.moneam.commonlibrary.mvp.base.FullScreenDialogFragment;
import com.moneam.commonlibrary.mvp.base.MessageType;
import com.moneam.commonlibrary.utils.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDialogFragment extends FullScreenDialogFragment {

    public static final String TAG = MessageDialogFragment.class.getSimpleName();
    @BindView(R2.id.imv_main)
    AppCompatImageView imvMain;
    @BindView(R2.id.imv_close)
    AppCompatImageView imvClose;
    @BindView(R2.id.tv_message)
    AppCompatTextView tvMessage;
    @BindView(R2.id.btn_done)
    Button btnDone;
    private MessageActionListener listener;
    private String message;
    private MessageType messageType;

    public MessageDialogFragment() {
    }

    public static MessageDialogFragment newInstance(String message,
                                                    MessageType messageType,
                                                    MessageActionListener listener) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        fragment.setMessage(message);
        fragment.setMessageType(messageType);
        fragment.setMessageActionListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_message_dialog;
    }

    @Override
    protected boolean showAsFullScreen() {
        return true;
    }

    @Override
    public void onViewReady(View view, Bundle savedInstanceState) {
        initView();
    }

    public void initView() {
        if (Preconditions.checkisNotNullOrEmpty(message) && tvMessage != null) {
            tvMessage.setText(message);
        }

        if (messageType != null && imvMain != null) {
            switch (messageType) {
                case Error:
                    break;
                case Done:
                    imvMain.setImageResource(R.drawable.ic_done);
                    break;
                case Warning:
                    break;
            }
        }
    }

    @OnClick({R2.id.imv_close, R2.id.btn_done})
    public void onClick(View view) {
        if (listener != null) {
            listener.OnActionClick();
            dismiss();
        }
        /*switch (view.getId()) {
            case R2.id.imv_close:
                break;
            case R2.id.btn_done:
                break;
        }*/
    }

    public void setMessageActionListener(MessageActionListener messageActionListener) {
        this.listener = messageActionListener;
    }

    public void setMessage(String message) {
        this.message = message;
        initView();
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
        initView();
    }

    public interface MessageActionListener {
        void OnActionClick();
    }
}
