package com.limefamily.recommend.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.activity.WebActivity;
import com.limefamily.recommend.model.News;
import com.limefamily.recommend.util.FormatUtil;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_NEWS = 1001;
    private static final int ITEM_HOT = 1002;

    private List<News> newsList = new ArrayList<>();
    private List<News> hotList = new ArrayList<>();

    public void setLimeNews(List<News> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged();
    }

    public void setHotRecommend(List<News> newsList) {
        this.hotList.addAll(newsList);
        notifyDataSetChanged();
    }

    public void clearData() {
        newsList.clear();
        hotList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_NEWS) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_header,null);
            return new HeaderViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hot,null);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).setData(newsList);
        }else if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).titleTextView.setText(hotList.get(position - 1).getNews_title());
            ((NormalViewHolder) holder).describeTextView.setText(hotList.get(position - 1).getNews_abstract());
            ((NormalViewHolder) holder).dateTextView.setText(FormatUtil.getInstance().
                    timestamp2DateString(hotList.get(position - 1).getNews_pub_time()));
            ((NormalViewHolder) holder).coverImageView.setImageURI(hotList.get(position - 1).getNews_img());
            ((NormalViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), WebActivity.class);
                    intent.putExtra(WebActivity.KEY_NEWS_ID,hotList.get(position - 1).getNews_id());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return hotList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_NEWS;
        }
        return ITEM_HOT;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        DiscreteScrollView scrollView;
        InfiniteScrollAdapter infiniteAdapter;
        NewsAdapter newsAdapter;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            scrollView = itemView.findViewById(R.id.scroll_view);
            scrollView.setOrientation(DSVOrientation.HORIZONTAL);
            newsAdapter = new NewsAdapter();
            infiniteAdapter = InfiniteScrollAdapter.wrap(newsAdapter);
            scrollView.setAdapter(infiniteAdapter);
            scrollView.setItemTransitionTimeMillis(150);
            scrollView.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
        }

        public void setData(List<News> newsList) {
            newsAdapter.setData(newsList);
            newsAdapter.notifyDataSetChanged();
            infiniteAdapter.notifyDataSetChanged();
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

         TextView titleTextView;
         TextView describeTextView;
         TextView dateTextView;
         SimpleDraweeView coverImageView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.iv_img);
            titleTextView = itemView.findViewById(R.id.tv_title);
            describeTextView = itemView.findViewById(R.id.tv_desc);
            dateTextView = itemView.findViewById(R.id.tv_date);
        }
    }
}
