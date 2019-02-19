package com.aaron.doubanmovie;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenll on 2016/10/13.
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context.getApplicationContext();
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mContext;
    }

}
