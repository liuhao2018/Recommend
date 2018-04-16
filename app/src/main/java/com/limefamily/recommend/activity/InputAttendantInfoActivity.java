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
import com.limefamily.recommend.logic.ApplicationBus;
import com.limefamily.recommend.logic.RefreshEvent;
import com.limefamily.recommend.model.Attendant;
import com.limefamily.recommend.restful.RecommendService;
import com.limefamily.recommend.util.FormatUtil;
import com.limefamily.recommend.util.PhoneUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InputAttendantInfoActivity extends AppCompatActivity {

    private String currentMode;
    public static final int SEX_MAN = 1;
    public static final int SEX_WOMAN = 2;
    public static final String MODE_NORMAL = "mode_normal";
    public static final String MODE_UPDATE = "mode_update";
    public static final String KEY_MODE = "key_mode";
    public static final String KEY_MODEL = "key_model";

    private ProgressBar progressBar;
    private TimePickerView timePickerView;
    private EditText attendantNameEditView,attendantPhoneEditText;
    private TextView attendantSexTextView,attendantBirthdayTextView,submitAttendantTextView,
            titleTextView,supportEditAttendantMobileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_attendant_info);
        progressBar = findViewById(R.id.pb);
        titleTextView = findViewById(R.id.tv_title);
        attendantNameEditView = findViewById(R.id.et_attendant_name);
        attendantPhoneEditText = findViewById(R.id.et_attendant_phone);
        attendantSexTextView = findViewById(R.id.tv_attendant_sex);
        attendantBirthdayTextView = findViewById(R.id.tv_attendant_birthday);
        submitAttendantTextView = findViewById(R.id.tv_recommend_attendant_submit);
        supportEditAttendantMobileTextView = findViewById(R.id.tv_support_edit_attendant_mobile);
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

        resolveMode();
    }

    private void resolveMode() {
        currentMode =  getIntent().getExtras().getString(KEY_MODE,"");
        if (MODE_NORMAL.equals(currentMode)) {
            currentMode = MODE_NORMAL;
            titleTextView.setText(getString(R.string.text_input_attendant));
            attendantPhoneEditText.setEnabled(true);
            supportEditAttendantMobileTextView.setVisibility(View.GONE);
        }else if (MODE_UPDATE.equals(currentMode)) {
            currentMode = MODE_UPDATE;
            titleTextView.setText(getString(R.string.text_edit_attendant));
            attendantPhoneEditText.setEnabled(false);
            supportEditAttendantMobileTextView.setVisibility(View.VISIBLE);
            Attendant attendant = (Attendant) getIntent().getExtras().getSerializable(KEY_MODEL);
            if (attendant == null) {
                Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT)
                        .show();
                finish();
                return;
            }else {
                writeAttendantData(attendant);
            }
        }else {
            currentMode = MODE_NORMAL;
        }
    }

    private void writeAttendantData(Attendant attendant) {
        attendantNameEditView.setText(attendant.getName());
        attendantPhoneEditText.setText(attendant.getMobile());
        attendantSexTextView.setText(FormatUtil.getInstance().formatSex(attendant.getSex()));
        attendantBirthdayTextView.setText(attendant.getBirthday());
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
            finish();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        RecommendService recommendService = retrofit.create(RecommendService.class);
        if (MODE_UPDATE.equals(currentMode)) {
            Call<Attendant> call = recommendService.updateAttendant(String.format("%s %s",getString(R.string.text_prefix_token),token),
                    new Attendant(name,sexNumber,birthday,mobile));
            call.enqueue(new Callback<Attendant>() {
                @Override
                public void onResponse(Call<Attendant> call, Response<Attendant> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_change_attendant_info_failed),Toast.LENGTH_SHORT).show();
                        }else {
                            if (response.body().getId() == 0) {
                                Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_change_attendant_info_failed),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_change_attendant_info_succeed),Toast.LENGTH_SHORT).show();
                                ApplicationBus.getInstance().post(new RefreshEvent());
                                finish();
                            }
                        }
                    }else {
                        Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_change_customer_info_failed),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Attendant> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(InputAttendantInfoActivity.this,getString(R.string.text_change_attendant_info_failed),Toast.LENGTH_SHORT).show();
                }
            });

        }else if (MODE_NORMAL.equals(currentMode)) {
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
        }else {
            Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    public void back(View view) {
        finish();
    }

}
