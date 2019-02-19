package com.aaron.doubanmovie.bz.in;

import android.content.Context;

import com.aaron.doubanmovie.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;
import me.aaron.dao.api.Api;

/**
 * Created by Chenll on 2016/10/13.
 */
@Module
public class InListFragmentModule {

    private InListFragmentPresenter.IView mView;

    public InListFragmentModule(InListFragmentPresenter.IView view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    InListFragmentPresenter providePresenter(Context context, Api api) {
        return new InListFragmentPresenterImpl(context, mView, api);
    }

}
