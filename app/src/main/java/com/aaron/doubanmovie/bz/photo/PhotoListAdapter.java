package com.aaron.doubanmovie.bz.photo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaron.doubanmovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aaronchan on 16/5/2.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_ITEM = 1;

    private List<String> mPhotos;

    public PhotoListAdapter(List<String> photos) {
        mPhotos = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_photo_list, parent, false);
            holder = new ItemViewHolder(itemView);
        } else {
            View emptyView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_empty, parent, false);
            holder = new EmptyViewHolder(emptyView);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String photo = mPhotos.get(position);

            Picasso.with(itemViewHolder.mImgPhoto.getContext())
                    .load(photo)
                    .placeholder(R.drawable.ic_image_white_24dp)
                    .into(itemViewHolder.mImgPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mPhotos.get(position) == null ? TYPE_EMPTY : TYPE_ITEM;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.img_photo)
        ImageView mImgPhoto;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> PhotoPreviewActivity.actionStart(v.getContext(), mPhotos.get(getLayoutPosition())));
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txt_empty)
        TextView mTextEmpty;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
