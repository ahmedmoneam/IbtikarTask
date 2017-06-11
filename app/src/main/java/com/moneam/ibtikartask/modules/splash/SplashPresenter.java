package com.moneam.ibtikartask.modules.splash;

import com.moneam.commonlibrary.mvp.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@SplashScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> implements SplashContract.Presenter {

    @Inject
    public SplashPresenter(SplashContract.View rootView, SplashContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void onViewReady() {
        Timber.d("onViewReady: %s", "start timeout");

        addSubscription(Observable.defer(() -> Observable.just(0)
                .delay(getModel().getDelayInSeconds(), TimeUnit.MILLISECONDS))
                .doOnNext(o -> {
                    if (getModel().isUserLogin()) {
                        getRootView().startMainActivity();
                        Timber.d("onViewReady: %s", "start main activity call");
                    } else {
                        getRootView().startLoginActivity();
                        Timber.d("onViewReady: %s", "start main login call");
                    }
                })
                .subscribe());
    }

}
