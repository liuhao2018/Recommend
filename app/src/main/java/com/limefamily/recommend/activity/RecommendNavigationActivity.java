package com.limefamily.recommend.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.limefamily.recommend.R;

public class RecommendNavigationActivity extends AppCompatActivity {

    public int recommendTypeDefault = 0;
    public static final int TYPE_CUSTOMER = 1;
    public static final int TYPE_ATTENDANT = 2;
    public static final String KEY_RECOMMEND_TYPE = "key_recommend_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_navigation);
        TextView titleTextView = findViewById(R.id.tv_title);
        View menuOneView = findViewById(R.id.rl_menu_one);
        View menuSecondView = findViewById(R.id.rl_menu_second);
        TextView menuOneTextView = findViewById(R.id.tv_menu_one);
        TextView menuSecondTextView = findViewById(R.id.tv_menu_second);
        int intentRecommend= getIntent().getIntExtra(KEY_RECOMMEND_TYPE,recommendTypeDefault);
        if (intentRecommend == TYPE_CUSTOMER) {
            titleTextView.setText(getString(R.string.text_recommend_customer));
            menuOneTextView.setText(getString(R.string.text_input_customer));
            menuSecondTextView.setText(getString(R.string.text_recommend_customer_list));
            menuOneView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecommendNavigationActivity.this,InputCustomerInfoActivity.class);
                    intent.putExtra(InputCustomerInfoActivity.KEY_MODE,InputCustomerInfoActivity.MODE_NORMAL);
                    startActivity(intent);
                }
            });
            menuSecondView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecommendNavigationActivity.this,RecommendListActivity.class);
                    intent.putExtra(KEY_RECOMMEND_TYPE,TYPE_CUSTOMER);
                    startActivity(intent);
                }
            });
        }
        else if (intentRecommend == TYPE_ATTENDANT) {
            titleTextView.setText(getString(R.string.text_recommend_attendant));
            menuOneTextView.setText(getString(R.string.text_input_attendant));
            menuSecondTextView.setText(getString(R.string.text_recommend_attendant_list));
            menuOneView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecommendNavigationActivity.this,InputAttendantInfoActivity.class);
                    intent.putExtra(InputAttendantInfoActivity.KEY_MODE,InputAttendantInfoActivity.MODE_NORMAL);
                    startActivity(intent);
                }
            });
            menuSecondView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecommendNavigationActivity.this,RecommendListActivity.class);
                    intent.putExtra(KEY_RECOMMEND_TYPE,TYPE_ATTENDANT);
                    startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    public void back(View view) {
        finish();
    }

}
