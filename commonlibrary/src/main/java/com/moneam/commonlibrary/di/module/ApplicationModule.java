package com.moneam.commonlibrary.di.module;

import android.app.Application;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.patloew.rxlocation.RxLocation;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import timber.log.Timber;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationScope
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationScope
    Context provideContext() {
        return mApplication.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .registerTypeAdapterFactory(CustomGsonAdapterFactory.create())
//                .registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory())
                .create();
    }

    @Provides
    @ApplicationScope
    LocationRequest provideLocationRequest() {
        return LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);
    }

    @Provides
    @ApplicationScope
    RxLocation provideRxLocation(Context context) {
        return new RxLocation(context);
    }

    @Provides
    @ApplicationScope
    Observable<Location> provideLocationObservable(RxLocation rxLocation,
                                                   LocationRequest locationRequest) {
        //noinspection MissingPermission
        return rxLocation.location()
                .updates(locationRequest)
                .filter(location -> {
                    Timber.d("provideLocationObservable() called with: location = " + location.toString());
                    return location.getAccuracy() < 70;
                });
    }
}
