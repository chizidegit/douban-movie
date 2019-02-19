package com.aaron.doubanmovie;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import me.aaron.dao.api.Api;
import me.aaron.dao.api.DaoModule;

/**
 * Created by Chenll on 2016/10/13.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DaoModule.class
})
public interface AppComponent {

    Context provideContext();

    Api provideApi();

}
