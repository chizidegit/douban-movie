package com.aaron.doubanmovie;

import android.app.Application;
import android.content.Context;

import me.aaron.dao.api.DaoModule;

/**
 * Created by aaronchan on 16/4/27.
 */
public class App extends Application {

    private static final String BASE_URL = "https://api.douban.com";

    private AppComponent mAppComponent;

    public static App getInstane(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .daoModule(new DaoModule(BASE_URL))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
