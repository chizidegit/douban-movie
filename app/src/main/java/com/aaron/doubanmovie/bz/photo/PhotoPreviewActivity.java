package com.aaron.doubanmovie.bz.photo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.common.BaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class PhotoPreviewActivity extends BaseActivity {

    private static final String EXTRA_IMG_URL = PhotoPreviewActivity.class.getName() + ".EXTRA_IMG_URL";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.img_photo)
    ImageView mPhoto;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static void actionStart(Context context, String imgUrl) {
        Intent intent = new Intent(context, PhotoPreviewActivity.class);
        intent.putExtra(EXTRA_IMG_URL, imgUrl);

        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_photo_preview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String imageUrl = getIntent().getStringExtra(EXTRA_IMG_URL);

        loadImage(imageUrl);
    }

    private void loadImage(String imageUrl) {
        mProgressBar.setVisibility(View.VISIBLE);

        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image_white_24dp)
                .into(mPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
