package com.moneam.ibtikartask.modules.splash;

import android.content.Intent;
import android.os.Bundle;

import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.mvp.base.BaseActivity;
import com.moneam.ibtikartask.R;
import com.moneam.ibtikartask.modules.authentication.login.LoginActivity;
import com.moneam.ibtikartask.modules.main.MainActivity;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

public class SplashActivity extends BaseActivity<SplashPresenter>
        implements SplashContract.View {

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void onViewReady(Bundle savedInstanceState, Intent intent) {
    }

    @Override
    protected void resolveDaggerDependency(ApplicationComponent appComponent) {
        DaggerSplashComponent
                .builder()
                .applicationComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void startMainActivity() {
        launchActivity(new Intent(this, MainActivity.class), true);
    }

    @Override
    public void startLoginActivity() {
        launchActivity(new Intent(this, LoginActivity.class), true);
    }
}
