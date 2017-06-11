package com.moneam.ibtikartask.modules.main;

import com.moneam.commonlibrary.di.scope.ActivityScope;
import com.moneam.commonlibrary.mvp.base.BasePresenter;
import com.moneam.commonlibrary.mvp.base.MessageType;
import com.moneam.ibtikartask.R;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 4/25/2017.
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View>
        implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.View rootView, MainContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void onViewReady() {
        getNextFollowerPage();
    }

    @Override
    public void getNextFollowerPage() {
        getNextFollowerPage(null);
    }

    @Override
    public void getNextFollowerPage(Consumer<List<User>> onNext) {
        addSubscription(getModel()
                .getFollowers()
                .doOnSubscribe(disposable -> getRootView().showLoading())
                .doAfterTerminate(() -> getRootView().hideLoading())
                .subscribeWith(getObserver(onNext)));
    }

    @Override
    public void onFollowerItemClick(User data) {
        getRootView().startFollowerInformationActivity(data);
    }

    @Override
    public void refreshFollowersList() {
        getModel().clearCachedData();
        getModel().resetCursor();
        addSubscription(getModel()
                .fetchFollowers()
                .doOnSubscribe(disposable -> getRootView().showLoading())
                .doAfterTerminate(() -> getRootView().hideLoading())
                .subscribeWith(getObserver(aVoid -> getRootView().clearList())));
    }

    @android.support.annotation.NonNull
    private DisposableObserver<List<User>> getObserver(final Consumer<List<User>> onNext) {
        return new DisposableObserver<List<User>>() {
            @Override
            public void onNext(@NonNull List<User> users) {
                if (onNext != null) {
                    try {
                        onNext.accept(users);
                    } catch (Exception e) {
                        Timber.e(e, "onNext:");
                    }
                }
                getRootView().updateList(users);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getRootView().showMessage(R.string.error_can_not_load_data, MessageType.Error);
                Timber.e(e, "onError: ");
            }

            @Override
            public void onComplete() {

            }
        };
    }
}