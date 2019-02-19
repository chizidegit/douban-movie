package com.aaron.doubanmovie.bz.in;

import com.aaron.doubanmovie.AppComponent;
import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by Chenll on 2016/10/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = InListFragmentModule.class)
public interface InListFragmentComponent {

    void inject(InListFragment fragment);

}
