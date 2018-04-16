package com.limefamily.recommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.limefamily.recommend.model.Rebate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2018/4/16.
 */

public class RebateListAdapter extends RecyclerView.Adapter<RebateListAdapter.ViewHolder> {

    private Context context;
    private List<Rebate> rebates;

    public RebateListAdapter(Context context) {
        this.context = context;
        rebates = new ArrayList<>();
    }

    public void appendData(List<Rebate> rebates) {
        this.rebates.addAll(rebates);
        notifyDataSetChanged();
    }

    public void clear() {
        this.rebates.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return rebates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
