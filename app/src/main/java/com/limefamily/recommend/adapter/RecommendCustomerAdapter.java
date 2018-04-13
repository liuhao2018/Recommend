package com.limefamily.recommend.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limefamily.recommend.R;
import com.limefamily.recommend.model.Customer;
import com.limefamily.recommend.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuhao on 2018/4/12.
 */

public class RecommendCustomerAdapter extends RecyclerView.Adapter<RecommendCustomerAdapter.ViewHolder> {

    private Context context;
    private List<Customer> customers;

    public RecommendCustomerAdapter(Context context) {
        customers = new ArrayList<>();
        this.context = context;
    }

    public void appendData(List<Customer> customers) {
        this.customers.addAll(customers);
        notifyDataSetChanged();
    }

    public void clearData() {
        customers.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_item_recommend_customer,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Customer customer = customers.get(position);
        if (customer != null ) {
            String createdDate = customer.getCreated_at();
            if (!TextUtils.isEmpty(createdDate)) {
                holder.customerRecommendDateTextView.setText(FormatUtil.getInstance().formatDetailDate(createdDate));
            }
            String statusStr = customer.getStatus();
            if (!TextUtils.isEmpty(statusStr)) {
                String[] statusArray = context.getResources().
                        getStringArray(R.array.array_recommend_status);
                if (statusArray[statusArray.length - 1].equals(statusStr) ||
                        statusArray[statusArray.length -2].equals(statusStr)) {
                    holder.customerRecommendStatusTextView.setTextColor(context.getResources().getColor(R.color.recommend_unable_status));
                }else {
                    holder.customerRecommendStatusTextView.setTextColor(context.getResources().getColor(R.color.recommend_normal_status));
                }
                holder.customerRecommendStatusTextView.setText(statusStr);
            }
            String customerName = customer.getName();
            if (!TextUtils.isEmpty(customerName)) {
                holder.customerNameTextView.setText(customerName);
            }
            holder.customerSexTextView.setText(FormatUtil.getInstance().formatSex(customer.getSex()));

            String birthday = customer.getBirthday();
            if (!TextUtils.isEmpty(birthday)) {
                holder.customerBirthdayTextView.setText(birthday);
            }
            String mobile = customer.getMobile();
            if (!TextUtils.isEmpty(mobile)) {
                holder.customerMobileTextView.setText(mobile);
            }
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView customerSexTextView;
        TextView customerNameTextView;
        TextView customerMobileTextView;
        TextView customerBirthdayTextView;
        TextView customerRecommendDateTextView;
        TextView customerRecommendStatusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            customerSexTextView = itemView.findViewById(R.id.tv_customer_sex);
            customerNameTextView = itemView.findViewById(R.id.tv_customer_name);
            customerMobileTextView = itemView.findViewById(R.id.tv_customer_mobile);
            customerBirthdayTextView = itemView.findViewById(R.id.tv_customer_birthday);
            customerRecommendDateTextView = itemView.findViewById(R.id.tv_recommend_date);
            customerRecommendStatusTextView = itemView.findViewById(R.id.tv_recommend_status);
        }
    }
}
