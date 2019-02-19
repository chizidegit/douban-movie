package com.aaron.doubanmovie.bz.photo;

import android.content.Context;

import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;
import me.aaron.dao.api.Api;

/**
 * Created by Chenll on 2016/10/13.
 */
@Module
public class PhotoWallActivityModule {

    private PhotoWallActivityPresenter.IView mView;

    public PhotoWallActivityModule(PhotoWallActivityPresenter.IView view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    PhotoWallActivityPresenter providePresenter(Context context, Api api) {
        return new PhotoWallActivityPresenterImpl(context, mView, api);
    }

}
