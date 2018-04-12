package com.limefamily.recommend.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.model.Attendant;
import com.limefamily.recommend.restful.RecommendService;
import com.limefamily.recommend.util.PhoneUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InputAttendantInfoActivity extends AppCompatActivity {

    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 2;

    private ProgressBar progressBar;
    private TimePickerView timePickerView;
    private EditText attendantNameEditView,attendantPhoneEditText;
    private TextView attendantSexTextView,attendantBirthdayTextView,submitAttendantTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_attendant_info);
        progressBar = findViewById(R.id.pb);
        attendantNameEditView = findViewById(R.id.et_attendant_name);
        attendantPhoneEditText = findViewById(R.id.et_attendant_phone);
        attendantSexTextView = findViewById(R.id.tv_attendant_sex);
        attendantBirthdayTextView = findViewById(R.id.tv_attendant_birthday);
        submitAttendantTextView = findViewById(R.id.tv_recommend_attendant_submit);
        attendantSexTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseSex();
            }
        });
        attendantBirthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseBirthday();
            }
        });
        submitAttendantTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAttendant();
            }
        });
    }

    private void chooseBirthday() {
        if (timePickerView == null) {
            timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    if (date != null) {
                        String birthday = getTime(date);
                        if (!TextUtils.isEmpty(birthday)) {
                            attendantBirthdayTextView.setText(birthday);
                        }
                    }
                }
            }).build();
        }
        timePickerView.show();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(date);
        return dataStr;
    }

    private void chooseSex() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_user_sex)
                .items(R.array.array_sex)
                .itemsCallbackSingleChoice(
                        0,new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                attendantSexTextView.setText(text);
                                return true;
                            }
                        }).positiveText(R.string.text_submit)
                .build()
                .show();
    }

    private void submitAttendant() {
        String name = attendantNameEditView.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,getString(R.string.text_must_input_name),Toast.LENGTH_SHORT).show();
            return;
        }
        String sex = attendantSexTextView.getText().toString();
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this,getString(R.string.text_must_input_sex),Toast.LENGTH_SHORT).show();
            return;
        }
        String birthday = attendantBirthdayTextView.getText().toString();
        if (TextUtils.isEmpty(birthday)) {
            Toast.makeText(this,getString(R.string.text_must_input_birthday),Toast.LENGTH_SHORT).show();
            return;
        }
        String mobile = attendantPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this,getString(R.string.text_must_input_phone),Toast.LENGTH_SHORT).show();
            return;
        }
        if (!PhoneUtil.isMobileExact(mobile)) {
            Toast.makeText(this,getString(R.string.text_mobile_syntax_error),Toast.LENGTH_SHORT).show();
            attendantPhoneEditText.setText(getString(R.string.text_empty));
            return;
        }
        int sexNumber = SEX_MAN;
        if (sex.equals(getResources().getStringArray(R.array.array_sex)[SEX_MAN - 1])) {
            sexNumber = SEX_MAN;
        }else if (sex.equals(getResources().getStringArray(R.array.array_sex)[SEX_WOMAN - 1])) {
            sexNumber = SEX_WOMAN;
        }
        String token = RecommendApplication.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        RecommendService recommendService = retrofit.create(RecommendService.class);
        Call<Attendant> call = recommendService.createAttendant(String.format("%s %s",getString(R.string.text_prefix_token),token),
                new Attendant(name,sexNumber,birthday,mobile));
        call.enqueue(new Callback<Attendant>() {
            @Override
            public void onResponse(Call<Attendant> call, Response<Attendant> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getId() == 0) {
                        Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_recommend_attendant_failed),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_recommend_attendant_succeed),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_recommend_attendant_failed),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Attendant> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_recommend_attendant_failed),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void back(View view) {
        finish();
    }

}
