package com.limefamily.recommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;

    public NewsAdapter() {
        this.newsList = new ArrayList<>();
    }

    public void setData(List<News> newsList) {
        this.newsList.addAll(newsList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_news,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.coverImageView.setImageURI(newsList.get(position).getCover());
        holder.titleTextView.setText(newsList.get(position).getTitle());
        holder.dateTextView.setText(newsList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView dateTextView;
        SimpleDraweeView coverImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.iv_img);
            titleTextView = itemView.findViewById(R.id.tv_title);
            dateTextView = itemView.findViewById(R.id.tv_date);
        }
    }
}
