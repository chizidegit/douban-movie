package com.aaron.doubanmovie.bz.top;

import com.aaron.doubanmovie.AppComponent;
import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by Chenll on 2016/10/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface TopListFragmentComponent {

    void inject(TopListFragment fragment);

}
