package com.aaron.doubanmovie.bz.celebrity;

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
import me.aaron.dao.model.Celebrity;

/**
 * Created by aaronchan on 16/5/2.
 */
public class CelebrityListAdapter extends RecyclerView.Adapter<CelebrityListAdapter.ViewHolder> {

    private List<Celebrity> mCelebrityList;

    public CelebrityListAdapter(List<Celebrity> celebrityList) {
        mCelebrityList = celebrityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_celebrity_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Celebrity celebrity = mCelebrityList.get(position);
        holder.mTxtName.setText(celebrity.getName());

        Picasso.with(holder.mImgAvatar.getContext())
                .load(celebrity.getAvatar().getMedium())
                .placeholder(R.drawable.ic_image_white_24dp)
                .into(holder.mImgAvatar);
    }

    @Override
    public int getItemCount() {
        return mCelebrityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.img_avatar)
        ImageView mImgAvatar;
        @Bind(R.id.txt_name)
        TextView mTxtName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
