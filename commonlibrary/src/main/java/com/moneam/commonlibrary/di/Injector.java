package com.moneam.commonlibrary.di;


import com.moneam.commonlibrary.application.BaseApplication;
import com.moneam.commonlibrary.di.component.ApplicationComponent;
import com.moneam.commonlibrary.di.component.DaggerApplicationComponent;
import com.moneam.commonlibrary.di.module.ApplicationModule;
import com.moneam.commonlibrary.di.module.LocalDataSourceModule;
import com.moneam.commonlibrary.di.module.NetworkModule;
import com.moneam.commonlibrary.di.module.RemoteDataSourceModule;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */

public enum Injector {
    INSTANCE;

    private ApplicationComponent applicationComponent;

    public ApplicationComponent initializeAppComponent(BaseApplication application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .networkModule(new NetworkModule(application.getBaseURL()))
                .localDataSourceModule(new LocalDataSourceModule())
                .remoteDataSourceModule(new RemoteDataSourceModule())
                .build();
        return applicationComponent;
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }
}
