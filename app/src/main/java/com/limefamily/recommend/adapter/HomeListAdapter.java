package com.limefamily.recommend.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limefamily.recommend.R;

/**
 * Created by liuhao on 2018/3/28.
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int ITEM_NEWS = 1001;
    public static final int Item_HOT = 1002;

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

        }else if (holder instanceof NormalViewHolder) {

        }
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_NEWS;
        }
        return Item_HOT;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
            manager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new NewsAdapter());
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
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
