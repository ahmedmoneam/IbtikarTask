package com.moneam.commonlibrary.di.component;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.moneam.commonlibrary.di.module.ApplicationModule;
import com.moneam.commonlibrary.di.module.LocalDataSourceModule;
import com.moneam.commonlibrary.di.module.NetworkModule;
import com.moneam.commonlibrary.di.module.RemoteDataSourceModule;
import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.moneam.commonlibrary.mvp.model.local.LocalDataSource;
import com.moneam.commonlibrary.mvp.model.remote.RemoteDataSource;
import com.patloew.rxlocation.RxLocation;

import java.util.Locale;

import dagger.Component;
import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, LocalDataSourceModule.class, RemoteDataSourceModule.class})
public interface ApplicationComponent {
    Context exposeContext();

    Gson exposeGson();

    LocationRequest exposeLocationRequest();

    RxLocation exposeRxLocation();

    Observable<Location> exposeLocationObservable();

    Locale exposeLocal();

    Retrofit exposeRetrofit();

    LocalDataSource exposeLocalDataSource();

    RemoteDataSource exposeRemoteDataSource();
}
