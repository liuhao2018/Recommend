package com.limefamily.recommend.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.model.Hot;
import com.limefamily.recommend.model.News;

import java.util.List;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> newsList;
    private List<Hot> hotList;

    public void setData(List<News> newsList,List<Hot> hotList) {
        this.newsList = newsList;
        this.hotList = hotList;
    }

    private static final int ITEM_NEWS = 1001;
    private static final int ITEM_HOT = 1002;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_NEWS) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_header,null);
            return new HeaderViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hot_recommend,null);
        return new NormalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).adapter.setData(newsList);
        }else if (holder instanceof NormalViewHolder) {
            ((NormalViewHolder) holder).tvTitle.setText(hotList.get(position - 1).getTitle());
            ((NormalViewHolder) holder).tvDesc.setText(hotList.get(position - 1).getDesc());
            ((NormalViewHolder) holder).tvDate.setText(hotList.get(position - 1).getDate());
            ((NormalViewHolder) holder).ivImage.setImageURI(hotList.get(position - 1).getCover());
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
        RecyclerView recyclerView;
        List<News> newsList;
        NewsAdapter adapter;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            manager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            adapter = new NewsAdapter();
            recyclerView.setAdapter(adapter);
        }

        public void setData(List<News> newsList) {
            this.newsList = newsList;
            adapter.notifyDataSetChanged();
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

         SimpleDraweeView ivImage;
         TextView tvTitle;
         TextView tvDesc;
         TextView tvDate;

        public NormalViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

}
