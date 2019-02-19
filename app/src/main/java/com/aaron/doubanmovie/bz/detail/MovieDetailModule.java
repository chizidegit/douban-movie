package com.aaron.doubanmovie.bz.detail;

import android.content.Context;

import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;
import me.aaron.dao.api.Api;

/**
 * Created by Chenll on 2016/10/13.
 */
@Module
public class MovieDetailModule {

    private MovieDetailActivityPresenter.IView mView;

    public MovieDetailModule(MovieDetailActivityPresenter.IView view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    MovieDetailActivityPresenter providePresenter(Context context, Api api) {
        return new MovieDetailActivityPresenterImpl(context, mView, api);
    }

}
