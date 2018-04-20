package com.limefamily.recommend.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.activity.WebActivity;
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
    public void onBindViewHolder(final  ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(newsList.get(position).getNews_img())) {
            holder.coverImageView.setImageURI(newsList.get(position).getNews_img());
        }
        holder.titleTextView.setText(newsList.get(position).getNews_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), WebActivity.class);
                intent.putExtra(WebActivity.KEY_NEWS_ID,newsList.get(position).getNews_id());
                holder.itemView.getContext().startActivity(intent);
            }
        });
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
            coverImageView = itemView.findViewById(R.id.iv_image);
            titleTextView = itemView.findViewById(R.id.tv_desc);
        }
    }
}
