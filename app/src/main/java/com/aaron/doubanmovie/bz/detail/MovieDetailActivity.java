package com.aaron.doubanmovie.bz.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aaron.doubanmovie.App;
import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.bz.celebrity.CelebrityListAdapter;
import com.aaron.doubanmovie.common.BaseActivity;
import com.aaron.doubanmovie.bz.photo.PhotoListAdapter;
import com.aaron.doubanmovie.bz.photo.PhotoWallActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import me.aaron.base.util.Logger;
import me.aaron.dao.model.Celebrity;

public class MovieDetailActivity extends BaseActivity implements MovieDetailActivityPresenter.IView {

    private static final Logger logger = new Logger(MovieDetailActivity.class);
    private static final String EXTRA_ID = MovieDetailActivity.class.getName() + ".EXTRA_ID";
    private static final String EXTRA_TITLE = MovieDetailActivity.class.getName() + ".EXTRA_TITLE";
    private static final String EXTRA_IMAGE_URL = MovieDetailActivity.class.getName() + ".EXTRA_IMAGE_URL";
    private static final String EXTRA_CELEBRITIES = MovieDetailActivity.class.getName() + "" + ".EXTRA_CELEBRITIES";

    @Bind(R.id.back_drop)
    ImageView mBackDrop;
    @Bind(R.id.summary)
    TextView mSummary;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.btn_summary_more)
    Button mExpand;
    @Bind(R.id.list_celebrity)
    RecyclerView mListCelebrity;
    @Bind(R.id.list_photo)
    RecyclerView mListPhoto;
    @Bind(R.id.btn_photo_more)
    Button mButtonPhotoMore;

    private String mId;
    private String mImageUrl;
    private String mTitle;
    private List<Celebrity> mCelebritis;
    private boolean mIsExpanded;
    private List<Celebrity> mCelebrities;
    private List<String> mPhotos;
    private CelebrityListAdapter mCelebrityAdapter;
    private PhotoListAdapter mPhotoAdapter;

    @Inject
    MovieDetailActivityPresenter mPresenter;

    public static void actionStart(Context context, String id, String title, String imageUrl, List<Celebrity> celebrities) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        intent.putParcelableArrayListExtra(EXTRA_CELEBRITIES, (ArrayList<? extends Parcelable>) celebrities);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        super.initData();

        mId = getIntent().getStringExtra(EXTRA_ID);
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mCelebritis = getIntent().getParcelableArrayListExtra(EXTRA_CELEBRITIES);

        mCelebrities = new ArrayList<>();
        mCelebrityAdapter = new CelebrityListAdapter(mCelebrities);

        mPhotos = new ArrayList<>();
        mPhotoAdapter = new PhotoListAdapter(mPhotos);

        mIsExpanded = false;
    }

    @Override
    protected void initDi() {
        super.initDi();
        DaggerMovieDetailActivityComponent
                .builder()
                .appComponent(App.getInstane(this).getAppComponent())
                .movieDetailModule(new MovieDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        super.initView();

        setStatusBarColor(android.R.color.transparent);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListCelebrity.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mListCelebrity.setAdapter(mCelebrityAdapter);

        mListPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mListPhoto.setAdapter(mPhotoAdapter);

        getSupportActionBar().setTitle(mTitle);

        loadBackDrop(mImageUrl);
        loadCelebrities(mCelebritis);

        mPresenter.fetchMovieDetail(mId);

        mPresenter.fetchMoviePhotos(mId, 5);
    }

    @OnClick(R.id.btn_summary_more)
    void onBtnExpandClicked() {
        if (!mIsExpanded) {
            expandSummaryText();
        } else {
            collapseSummaryText();
        }
    }
    @OnClick(R.id.btn_photo_more)
    void onButtonMoreClicked() {
        PhotoWallActivity.actionStart(this, mTitle, mId, mPhotos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void loadBackDrop(String url) {
        Picasso.with(this)
                .load(url)
                .into(mBackDrop);
    }

    private void loadCelebrities(List<Celebrity> celebrities) {
        mCelebrities.addAll(celebrities);
        mCelebrityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        mPhotos.add(null);
        mPhotoAdapter.notifyDataSetChanged();

        mButtonPhotoMore.setVisibility(View.INVISIBLE);
    }

    @Override
    public void refreshPhotos(List<String> photos) {
        mPhotos.addAll(photos);
        mPhotoAdapter.notifyDataSetChanged();
    }

    @Override
    public void expandSummaryText() {
        Animator animator = ObjectAnimator.ofInt(mSummary, "maxLines", mSummary
                .getLineCount())
                .setDuration(200);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mExpand.setText(R.string.btn_collapse);
                mIsExpanded = true;
            }
        });

        animator.start();
    }

    @Override
    public void collapseSummaryText() {
        Animator animator = ObjectAnimator.ofInt(mSummary, "maxLines", mSummary.getLineCount(), 4)
                .setDuration(200);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mExpand.setText(R.string.btn_more);
                mIsExpanded = false;
            }
        });

        animator.start();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setSummary(String summary) {
        mSummary.setText(summary);
    }

}
