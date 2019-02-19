package me.aaron.dao.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.aaron.dao.api.retrofit.ApiErrorAwareConverterFactory;
import me.aaron.dao.api.retrofit.EmptyJsonConverterFactory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chenll on 2016/10/13.
 */
@Module
public class DaoModule {

    private String mBaseUrl;

    public DaoModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Singleton
    @Provides
    Cache provideOkHttpCache(Context context) {
        int size = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), size);
        return cache;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(new ApiErrorAwareConverterFactory(
                        new EmptyJsonConverterFactory(GsonConverterFactory.create(gson))
                ))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    Api provideApi(ApiService apiService, OkHttpClient okHttpClient) {
        return new ApiImpl(apiService, okHttpClient);
    }

}
