package com.aaron.doubanmovie.bz.detail;

import android.content.Context;

import com.aaron.doubanmovie.common.ExceptionHandler;
import com.aaron.doubanmovie.util.LogUtil;

import java.util.Random;

import me.aaron.dao.api.Api;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.ContentValues.TAG;
import static me.aaron.base.util.Utils.checkNotNull;

/**
 * Created by Chenll on 2016/10/13.
 */
public class MovieDetailActivityPresenterImpl implements MovieDetailActivityPresenter {

    private Context mContext;
    private Api mApi;
    private IView mView;
    private CompositeSubscription mAllSubscription;

    public MovieDetailActivityPresenterImpl(Context context, IView view, Api api) {
        mContext = checkNotNull(context, "context == null");
        mView = checkNotNull(view, "view == null");
        mApi = checkNotNull(api, "api == null");

        mAllSubscription = new CompositeSubscription();
    }

    @Override
    public void fetchMovieDetail(String id) {
        mView.showProgressBar();

        Subscription subscription = mApi.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    mView.hideProgressBar();

                    mView.setSummary(movie.getSummary());
                }, throwable -> {
                    LogUtil.error(TAG, "fetchMovieDetail", throwable);
                    ExceptionHandler.handleHttpException(mContext, (HttpException) throwable);
                });
        mAllSubscription.add(subscription);
    }

    @Override
    public void fetchMoviePhotos(String id, int count) {
        mApi.getMoviePhotos(id, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photos -> {
                    if (photos.size() == 0) {
                        mView.showEmptyView();
                        return;
                    }

                    mView.refreshPhotos(photos);

                    String randomPhoto = photos.get(new Random().nextInt(photos.size()));
                    mView.loadBackDrop(randomPhoto);
                }, throwable -> {
                    LogUtil.error(TAG, "fetchMoviePhotos", throwable);
                    ExceptionHandler.handleHttpException(mContext, (HttpException) throwable);

                    mView.showEmptyView();
                });
    }

    @Override
    public void onDestroy() {
        if (!mAllSubscription.isUnsubscribed()) {
            mAllSubscription.unsubscribe();
        }
    }

}
