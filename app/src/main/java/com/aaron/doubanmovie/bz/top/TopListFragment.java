package com.aaron.doubanmovie.bz.top;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.aaron.doubanmovie.App;
import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.common.BaseFragment;
import com.aaron.doubanmovie.common.MovieListAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import me.aaron.base.util.Logger;
import me.aaron.dao.api.Api;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aaronchan on 16/4/27.
 */
public class TopListFragment extends BaseFragment {
    private static final Logger logger = new Logger(TopListFragment.class);

    @Bind(R.id.list_movies)
    RecyclerView mListMovies;
    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    Api mApi;

    private MovieListAdapter mAdapter;

    public static TopListFragment newInstance() {
        TopListFragment fragment = new TopListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_movie_list;
    }

    @Override
    protected void initData() {
        super.initData();

        mAdapter = new MovieListAdapter();
    }

    @Override
    protected void initDi() {
        super.initDi();
        DaggerTopListFragmentComponent
                .builder()
                .appComponent(App.getInstane(getContext()).getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        super.initView();

        mSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipe.setOnRefreshListener(() -> fetchMovies());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mListMovies.setLayoutManager(layoutManager);

        mAdapter.bindRecyclerView(mListMovies);
        mAdapter.setOnLoadMoreListener(() -> fetchMoreMovies());

        mListMovies.setAdapter(mAdapter);

        mProgressBar.setVisibility(mAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);

        fetchMovies();
    }

    private void fetchMovies() {
        addSubscription(
                mApi.getTop(0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(top -> {
                    logger.debug("swipe is " + mSwipe);
                    mSwipe.setRefreshing(false);
                    mProgressBar.setVisibility(View.GONE);

                    mAdapter.setMovies(top.getMovies());
                    mAdapter.notifyDataSetChanged();
                }, logger::error)
        );
    }

    private void fetchMoreMovies() {
        // add progress item
        mAdapter.getMovies().add(null);
        mAdapter.notifyItemInserted(mAdapter.getMovies().size() - 1);

        final int currentSize = mAdapter.getItemCount();

        addSubscription(mApi.getTop(currentSize, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(top -> {
                    mAdapter.getMovies().remove(mAdapter.getMovies().size() - 1);
                    mAdapter.notifyItemRemoved(mAdapter.getMovies().size());

                    mAdapter.getMovies().addAll(top.getMovies());
                    mAdapter.notifyItemRangeInserted(currentSize, currentSize + 20);

                    mAdapter.setLoaded();
                }, logger::error)
        );
    }
}
