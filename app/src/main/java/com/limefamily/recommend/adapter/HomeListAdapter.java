package com.limefamily.recommend.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.model.Hot;
import com.limefamily.recommend.model.News;
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
    private List<Hot> hotList = new ArrayList<>();

    public void setData(List<News> newsList,List<Hot> hotList) {
        this.newsList.addAll(newsList);
        this.hotList.addAll(hotList);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).setData(newsList);
        }else if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).titleTextView.setText(hotList.get(position - 1).getTitle());
            ((NormalViewHolder) holder).describeTextView.setText(hotList.get(position - 1).getDesc());
            ((NormalViewHolder) holder).dateTextView.setText(hotList.get(position - 1).getDate());
            ((NormalViewHolder) holder).coverImageView.setImageURI(hotList.get(position - 1).getCover());
        }
    }

    @Override
    public int getItemCount() {
        if (newsList.size() == 0 && hotList.size() == 0) {
            return 0;
        }
        else if (newsList.size() == 0 && hotList.size() > 0 ) {
            return hotList.size();
        }
        else {
            return 1;
        }
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
        List<News> newsList = new ArrayList<>();

        public HeaderViewHolder(View itemView) {
            super(itemView);
            scrollView = itemView.findViewById(R.id.scroll_view);
            scrollView.setOrientation(DSVOrientation.HORIZONTAL);
            infiniteAdapter = InfiniteScrollAdapter.wrap(new NewsAdapter());
            scrollView.setAdapter(infiniteAdapter);
            scrollView.setItemTransitionTimeMillis(150);
            scrollView.setItemTransformer(new ScaleTransformer.Builder()
                    .setMinScale(0.8f)
                    .build());
        }

        public void setData(List<News> newsList) {
            this.newsList = newsList;
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
