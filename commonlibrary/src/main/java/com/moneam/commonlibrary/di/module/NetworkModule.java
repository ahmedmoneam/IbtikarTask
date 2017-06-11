package com.moneam.commonlibrary.di.module;

import com.google.gson.Gson;
import com.moneam.commonlibrary.di.scope.ApplicationScope;
import com.moneam.commonlibrary.utils.LocalLogger;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmed Abdelmoneam on 12/1/2016.
 */
@Module
public class NetworkModule {
    private static final int TIMEOUT = 6;
    private final String mBaseUrl;

    public NetworkModule(String baseURL) {
        mBaseUrl = baseURL;
    }

    @Provides
    @ApplicationScope
    Locale provideLocale() {
        return Locale.getDefault();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(
                new LocalLogger()).setLevel(
                HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }
}
