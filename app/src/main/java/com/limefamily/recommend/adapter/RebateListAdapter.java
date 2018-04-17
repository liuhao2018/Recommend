package com.limefamily.recommend.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limefamily.recommend.R;
import com.limefamily.recommend.model.Rebate;
import com.limefamily.recommend.model.RebateResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2018/4/16.
 */

public class RebateListAdapter extends RecyclerView.Adapter<RebateListAdapter.ViewHolder> {

    private Context context;
    private List<RebateResponseModel> rebates;

    public RebateListAdapter(Context context) {
        this.context = context;
        rebates = new ArrayList<>();
    }

    public void appendData(List<RebateResponseModel> rebates) {
        this.rebates.addAll(rebates);
        notifyDataSetChanged();
    }

    public void clear() {
        this.rebates.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_rebate,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RebateResponseModel rebateResponseModel = rebates.get(position);
        if (rebateResponseModel != null ) {
            holder.rebateMoneyTextView.setText(String.format("%d%s",
                    rebateResponseModel.getAmount()/100,context.getString(R.string.text_money_unit)));
            String rebateStatus = rebateResponseModel.getStatus();
            if (!TextUtils.isEmpty(rebateStatus)) {
                holder.rebateStatusTextView.setText(rebateStatus);
            }
            String rebateName = rebateResponseModel.getName();
            if (!TextUtils.isEmpty(rebateName)) {
                holder.rebatedNameTextView.setText(rebateName);
            }
            int rebateType = rebateResponseModel.getType();
            if (rebateType == 0) {
                holder.rebateTypeTextView.setText(context.getString(R.string.text_customer));
            }else if (rebateType == 1){
                holder.rebateTypeTextView.setText(context.getString(R.string.text_attendant));
            }
        }
    }

    @Override
    public int getItemCount() {
        return rebates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView rebateMoneyTextView;
        TextView rebateStatusTextView;
        TextView rebatedNameTextView;
        TextView rebateTypeTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            rebateMoneyTextView = itemView.findViewById(R.id.tv_user_rebate_money);
            rebateStatusTextView = itemView.findViewById(R.id.tv_rebate_status);
            rebatedNameTextView = itemView.findViewById(R.id.tv_rebate_name);
            rebateTypeTextView = itemView.findViewById(R.id.tv_rebate_type);
        }
    }

}
