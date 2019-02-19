package com.aaron.doubanmovie.bz.detail;

import com.aaron.doubanmovie.AppComponent;
import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by Chenll on 2016/10/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MovieDetailModule.class)
public interface MovieDetailActivityComponent {

    void inject(MovieDetailActivity activity);

}
