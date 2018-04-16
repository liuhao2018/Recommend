package com.limefamily.recommend.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limefamily.recommend.R;
import com.limefamily.recommend.activity.InputAttendantInfoActivity;
import com.limefamily.recommend.model.Attendant;
import com.limefamily.recommend.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhao on 2018/4/13.
 */

public class RecommendAttendantAdapter extends RecyclerView.Adapter<RecommendAttendantAdapter.ViewHolder> {

    private Context context;
    private List<Attendant> attendants;

    public RecommendAttendantAdapter(Context context) {
        attendants = new ArrayList<>();
        this.context = context;
    }

    public void appendData(List<Attendant> attendants) {
        this.attendants.addAll(attendants);
        notifyDataSetChanged();
    }

    public void clearData() {
        attendants.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_item_recommend_attendant,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Attendant attendant = attendants.get(position);
        if (attendant != null ) {
            String createdDate = attendant.getCreated_at();
            if (!TextUtils.isEmpty(createdDate)) {
                holder.attendantRecommendDateTextView.setText(FormatUtil.getInstance().formatDetailDate(createdDate));
            }
            String statusStr = attendant.getStatus();
            if (!TextUtils.isEmpty(statusStr)) {
                String[] statusArray = context.getResources().
                        getStringArray(R.array.array_recommend_status);
                if (statusArray[statusArray.length - 1].equals(statusStr) ||
                        statusArray[statusArray.length -2].equals(statusStr)) {
                    holder.attendantRecommendStatusTextView.setTextColor(context.getResources().getColor(R.color.recommend_unable_status));
                }else {
                    holder.attendantRecommendStatusTextView.setTextColor(context.getResources().getColor(R.color.recommend_normal_status));
                }
                holder.attendantRecommendStatusTextView.setText(statusStr);
            }
            String customerName = attendant.getName();
            if (!TextUtils.isEmpty(customerName)) {
                holder.attendantNameTextView.setText(customerName);
            }
            holder.attendantSexTextView.setText(FormatUtil.getInstance().formatSex(attendant.getSex()));

            String birthday = attendant.getBirthday();
            if (!TextUtils.isEmpty(birthday)) {
                holder.attendantBirthdayTextView.setText(birthday);
            }
            String mobile = attendant.getMobile();
            if (!TextUtils.isEmpty(mobile)) {
                holder.attendantMobileTextView.setText(mobile);
            }
            holder.attendantEditedTextView.setVisibility(View.VISIBLE);
            holder.attendantEditedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,InputAttendantInfoActivity.class);
                    intent.putExtra(InputAttendantInfoActivity.KEY_MODE,InputAttendantInfoActivity.MODE_UPDATE);
                    intent.putExtra(InputAttendantInfoActivity.KEY_MODEL,attendant);
                    context.startActivity(intent);
                }
            });
        }else {
            holder.attendantEditedTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return attendants.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView attendantEditedTextView;
        TextView attendantSexTextView;
        TextView attendantNameTextView;
        TextView attendantMobileTextView;
        TextView attendantBirthdayTextView;
        TextView attendantRecommendDateTextView;
        TextView attendantRecommendStatusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            attendantEditedTextView = itemView.findViewById(R.id.tv_edit_attendant);
            attendantSexTextView = itemView.findViewById(R.id.tv_attendant_sex);
            attendantNameTextView = itemView.findViewById(R.id.tv_attendant_name);
            attendantMobileTextView = itemView.findViewById(R.id.tv_attendant_mobile);
            attendantBirthdayTextView = itemView.findViewById(R.id.tv_attendant_birthday);
            attendantRecommendDateTextView = itemView.findViewById(R.id.tv_recommend_date);
            attendantRecommendStatusTextView = itemView.findViewById(R.id.tv_recommend_status);
        }
    }

}
