package com.aaron.doubanmovie.bz.in;

import android.content.Context;

import com.aaron.doubanmovie.common.ExceptionHandler;
import com.aaron.doubanmovie.util.LogUtil;

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
public class InListFragmentPresenterImpl implements InListFragmentPresenter {

    private Context mContext;
    private Api mApi;
    private IView mView;
    private CompositeSubscription mAllSubscription;

    public InListFragmentPresenterImpl(Context context, IView view, Api api) {
        mContext =  checkNotNull(context, "context == null");
        mView = checkNotNull(view, "view == null");
        mApi = checkNotNull(api, "api == null");

        mAllSubscription = new CompositeSubscription();
    }

    @Override
    public void fetchMovies(String city) {
        Subscription subscription = mApi.getInTheaters(city, 0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inTheater -> {
                    mView.hideProgressBar();
                    mView.refreshMovies(inTheater.getMovies());
                }, throwable -> {
                    LogUtil.error(TAG, "fetchMovies", throwable);
                    if (throwable instanceof HttpException) {
                        ExceptionHandler.handleHttpException(mContext, (HttpException) throwable);
                    }
                });
        mAllSubscription.add(subscription);
    }

    @Override
    public void fetchMoreMovies(String city) {
        mView.addRefreshProgress();

        final int currentSize = mView.getItemsCount();

        Subscription subscription = mApi.getInTheaters(city, currentSize, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inTheater -> {
                    mView.removeRefreshProgress();
                    mView.loadMoreMovies(currentSize, inTheater.getMovies());
                }, throwable -> {
                    LogUtil.error(TAG, "fetchMoreMovies", throwable);
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
