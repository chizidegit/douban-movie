package com.aaron.doubanmovie.bz.in;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.aaron.doubanmovie.App;
import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.common.BaseFragment;
import com.aaron.doubanmovie.common.MovieListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import me.aaron.base.util.Logger;
import me.aaron.dao.model.Movie;

/**
 * Created by aaronchan on 16/4/27.
 */
public class InListFragment extends BaseFragment implements InListFragmentPresenter.IView {
    private static final Logger logger = new Logger(InListFragment.class);
    private static final String CITY = "福州";

    @Bind(R.id.list_movies)
    RecyclerView mListMovies;
    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private MovieListAdapter mAdapter;

    @Inject
    InListFragmentPresenter mPresenter;

    public static InListFragment newInstance() {
        return new InListFragment();
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
        DaggerInListFragmentComponent
                .builder()
                .appComponent(App.getInstane(getContext()).getAppComponent())
                .inListFragmentModule(new InListFragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        super.initView();

        mSwipe.setOnRefreshListener(() -> mPresenter.fetchMovies(CITY));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mListMovies.setLayoutManager(layoutManager);

        mAdapter.bindRecyclerView(mListMovies);
        mAdapter.setOnLoadMoreListener(() -> mPresenter.fetchMoreMovies(CITY));
        mListMovies.setAdapter(mAdapter);

        mSwipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimaryDark);

        mProgressBar.setVisibility(mAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);

        mPresenter.fetchMovies(CITY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    public void refreshMovies() {
        mSwipe.setRefreshing(true);
        mListMovies.smoothScrollToPosition(0);

        mPresenter.fetchMovies(CITY);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mSwipe.setRefreshing(false);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshMovies(List<Movie> movies) {
        mAdapter.setMovies(movies);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addRefreshProgress() {
        mAdapter.getMovies().add(null);
        mAdapter.notifyItemInserted(mAdapter.getMovies().size() - 1);
    }

    @Override
    public void removeRefreshProgress() {
        mAdapter.getMovies().remove(mAdapter.getMovies().size() - 1);
        mAdapter.notifyItemRemoved(mAdapter.getMovies().size());
        mAdapter.setLoaded();
    }

    @Override
    public int getItemsCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public void loadMoreMovies(int currentSize, List<Movie> movies) {
        mAdapter.getMovies().addAll(movies);
        mAdapter.notifyItemRangeInserted(currentSize, currentSize + 20);
    }

}
