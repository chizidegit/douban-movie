package com.aaron.doubanmovie.bz.photo;

import android.content.Context;

import com.aaron.doubanmovie.util.LogUtil;

import me.aaron.dao.api.Api;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.ContentValues.TAG;
import static me.aaron.base.util.Utils.checkNotNull;

/**
 * Created by Chenll on 2016/10/13.
 */
public class PhotoWallActivityPresenterImpl implements PhotoWallActivityPresenter {

    private Context mContext;
    private Api mApi;

    private IView mView;
    private CompositeSubscription mAllSubscription;

    public PhotoWallActivityPresenterImpl(Context context, IView view, Api api) {
        mContext =  checkNotNull(context, "context == null");
        mView = checkNotNull(view, "view == null");
        mApi = checkNotNull(api, "api == null");

        mAllSubscription = new CompositeSubscription();
    }

    @Override
    public void fetchAllPhotos(String id) {
        mView.showProgressBar();

        Subscription subscription = mApi.getMoviePhotos(id, Integer.MAX_VALUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> {
                    mView.hideProgressBar();
                    mView.showAllPhotos(photos);
                }, throwable -> {
                    LogUtil.error(TAG, "fetchAllPhotos", throwable);
                    mView.hideProgressBar();
                });
        mAllSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        if (!mAllSubscription.isUnsubscribed()) {
            mAllSubscription.unsubscribe();
        }
    }

}
