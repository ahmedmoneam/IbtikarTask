package com.moneam.ibtikartask.modules.authentication.login;

import android.content.Intent;
import android.os.Bundle;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.mvp.base.BaseActivity;
import com.moneam.ibtikartask.R;
import com.moneam.ibtikartask.modules.main.MainActivity;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.btn_twitterLogin)
    TwitterLoginButton btnTwitterLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void onViewReady(Bundle savedInstanceState, Intent intent) {
        btnTwitterLogin.setCallback(getPresenter().twitterLoginCallback());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnTwitterLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void resolveDaggerDependency(ApplicationComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .applicationComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void startMainActivity() {
        launchActivity(new Intent(this, MainActivity.class), true);
    }
}
