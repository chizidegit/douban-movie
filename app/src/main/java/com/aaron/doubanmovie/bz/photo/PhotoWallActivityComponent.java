package com.aaron.doubanmovie.bz.photo;

import com.aaron.doubanmovie.AppComponent;
import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by Chenll on 2016/10/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = PhotoWallActivityModule.class)
public interface PhotoWallActivityComponent {

    void inject(PhotoWallActivity activity);

}
