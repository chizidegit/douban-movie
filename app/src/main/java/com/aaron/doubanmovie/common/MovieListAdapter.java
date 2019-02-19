package com.aaron.doubanmovie.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.bz.detail.MovieDetailActivity;
import com.aaron.doubanmovie.util.MovieParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.aaron.base.util.Logger;
import me.aaron.dao.model.Celebrity;
import me.aaron.dao.model.Movie;

/**
 * Created by Git on 2016/1/23.
 */
public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final Logger logger = new Logger(MovieListAdapter.class);
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_LOADING = 1;
    private static final int VISIBLE_THRESHOLD = 5;

    private List<Movie> mMovies;
    private boolean mIsLoading;
    private OnLoadMoreCallback mOnLoadMoreListener;

    public MovieListAdapter() {
        mMovies = new ArrayList<>();
    }

    /**
     * 在 RecyclerView 设置 LayoutManager 之后调用
     * @param recyclerView
     */
    public void bindRecyclerView(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!mIsLoading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                        // End has been reached
                        // Do something
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        mIsLoading = true;
                    }
                }
            });
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreCallback onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    public void setLoaded() {
        mIsLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        if (viewType == VIEW_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie_list, parent, false);
            holder = new ItemViewHolder(itemView);
        } else {
            View loadingView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            holder = new LoadingViewHolder(loadingView);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_ITEM) {
            final Movie movie = mMovies.get(position);

            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.mTitle.setText(movie.getTitle());
            itemHolder.mYear.setText(movie.getYear());

            double average = movie.getRating().getAverage();
            double max = movie.getRating().getMax();
            float rating = (float) (average / max);

            itemHolder.mRatingBar.setRating(rating * itemHolder.mRatingBar.getNumStars());
            Context context = itemHolder.mRatingValue.getContext();
            itemHolder.mRatingValue.setText(rating == 0.0 ? context.getString(R.string.label_rating_unavailable) :
                    String.format(Locale.CHINA, "%.1f", average));

            String genres = MovieParser.parseGenres(movie.getGenres());
            itemHolder.mType.setText(genres);

            String casts = MovieParser.parseCelebrities(movie.getCasts());
            itemHolder.mCasts.setText(casts);

            String imageUrl = movie.getImages().getLarge();

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int defaultColor = ContextCompat.getColor(itemHolder.mCardView.getContext(), R.color.colorPrimary);
                            int backgroundColor = palette.getMutedColor(defaultColor);

                            itemHolder.mCardView.setCardBackgroundColor(backgroundColor);
                            itemHolder.mImage.setImageBitmap(bitmap);
                        }
                    });
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            itemHolder.mImage.setTag(target);
            Picasso.with(itemHolder.mImage.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_image_white_24dp)
                    .into(target);
        } else {
            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            loadingHolder.mLoading.setIndeterminate(true);
        }
    }

    /**
     * 下拉刷新的进度条也算一个列表项
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position) != null ? VIEW_ITEM : VIEW_LOADING;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title)
        TextView mTitle;
        @Bind(R.id.year)
        TextView mYear;
        @Bind(R.id.image)
        ImageView mImage;
        @Bind(R.id.rating_value)
        TextView mRatingValue;
        @Bind(R.id.rating_bar)
        RatingBar mRatingBar;
        @Bind(R.id.type)
        TextView mType;
        @Bind(R.id.casts)
        TextView mCasts;
        @Bind(R.id.card)
        CardView mCardView;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                Movie movie = mMovies.get(getLayoutPosition());

                List<Celebrity> celebrities = new ArrayList<>();
                celebrities.addAll(movie.getDirectors());
                celebrities.addAll(movie.getCasts());

                MovieDetailActivity.actionStart(itemView.getContext(),
                        movie.getId(),
                        movie.getTitle(),
                        movie.getImages().getLarge(),
                        celebrities);
            });
        }

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.loading)
        ProgressBar mLoading;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnLoadMoreCallback {
        void onLoadMore();
    }
}
