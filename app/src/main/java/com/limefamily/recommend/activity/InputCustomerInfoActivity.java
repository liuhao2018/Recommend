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
import com.limefamily.recommend.model.Customer;
import com.limefamily.recommend.restful.RecommendService;
import com.limefamily.recommend.util.FormatUtil;
import com.limefamily.recommend.util.PhoneUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InputCustomerInfoActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TimePickerView timePickerView;
    private EditText customerNameEditView,customerPhoneEditText,customerContactEditView;
    private TextView customerSexTextView,customerBirthdayTextView,customerWithContactTextView,
            customerCareIntentTextView,submitCustomerTextView;

    public static final String MODE_NORMAL = "mode_normal";
    public static final String MODE_UPDATE = "mode_update";
    public static final String KEY_MODE = "key_mode";
    public static final String KSY_MODEL = "key_model";

    private String currentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_customer_info);
        progressBar = findViewById(R.id.pb);
        customerNameEditView = findViewById(R.id.et_customer_name);
        customerPhoneEditText = findViewById(R.id.et_customer_phone);
        customerContactEditView = findViewById(R.id.et_customer_contact);
        customerSexTextView = findViewById(R.id.tv_customer_sex);
        customerBirthdayTextView = findViewById(R.id.tv_customer_birthday);
        customerWithContactTextView = findViewById(R.id.tv_customer_with_contact);
        customerCareIntentTextView = findViewById(R.id.tv_customer_intent);
        submitCustomerTextView = findViewById(R.id.tv_recommend_customer_submit);
        customerSexTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseSex();
            }
        });
        customerBirthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseBirthday();
            }
        });
        customerWithContactTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCustomerWithContact();
            }
        });
        customerCareIntentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCareIntent();
            }
        });
        submitCustomerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCustomer();
            }
        });

        resolveMode();

    }

    private void resolveMode() {
        currentMode =  getIntent().getExtras().getString(KEY_MODE,"");
        if (MODE_NORMAL.equals(currentMode)) {
            currentMode = MODE_NORMAL;
        }else if (MODE_UPDATE.equals(currentMode)) {
            currentMode = MODE_UPDATE;
            Customer customer = (Customer) getIntent().getExtras().getSerializable(KSY_MODEL);
            if (customer == null) {
                Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
                finish();
                return;
            }else {
                writeCustomerData(customer);
            }
        }else {
            currentMode = MODE_NORMAL;
        }
    }

    private void writeCustomerData(Customer customer) {
        customerNameEditView.setText(customer.getName());
        customerPhoneEditText.setText(customer.getMobile());
        customerContactEditView.setText(customer.getContact());
        customerCareIntentTextView.setText(customer.getIntention());
        customerWithContactTextView.setText(customer.getContact_relation());
        customerSexTextView.setText(FormatUtil.getInstance().formatSex(customer.getSex()));
        customerBirthdayTextView.setText(FormatUtil.getInstance().formatDetailDate(customer.getBirthday()));
    }


    private void chooseSex() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_user_sex)
                .items(R.array.array_sex)
                .itemsCallbackSingleChoice(
                        0,new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                customerSexTextView.setText(text);
                                return true;
                            }
                        }).positiveText(R.string.text_submit)
                .build()
                .show();
    }
    private void chooseBirthday() {
        if (timePickerView == null) {
            timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    if (date != null) {
                        String birthday = getTime(date);
                        if (!TextUtils.isEmpty(birthday)) {
                            customerBirthdayTextView.setText(birthday);
                        }
                    }
                }
            }).build();
        }
        timePickerView.show();
    }

    private void chooseCustomerWithContact() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_input_customer_with_contact)
                .items(R.array.array_customer_with_contact)
                .itemsCallbackSingleChoice(
                        0,new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                customerWithContactTextView.setText(text);
                                return true;
                            }
                        }).positiveText(R.string.text_submit)
                .build()
                .show();
    }

    private void chooseCareIntent() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_input_customer_care_intent)
                .items(R.array.array_care_intent)
                .itemsCallbackSingleChoice(
                        0,new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                customerCareIntentTextView.setText(text);
                                return true;
                            }
                        }).positiveText(R.string.text_submit)
                .build()
                .show();
    }

    private void submitCustomer() {
        String name = customerNameEditView.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,getString(R.string.text_must_input_name),Toast.LENGTH_SHORT).show();
            return;
        }
        String sex = customerSexTextView.getText().toString();
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this,getString(R.string.text_must_input_sex),Toast.LENGTH_SHORT).show();
            return;
        }
        String birthday = customerBirthdayTextView.getText().toString();
        if (TextUtils.isEmpty(birthday)) {
            Toast.makeText(this,getString(R.string.text_must_input_birthday),Toast.LENGTH_SHORT).show();
            return;
        }
        String contact = customerContactEditView.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            Toast.makeText(this,getString(R.string.text_must_input_contact),Toast.LENGTH_SHORT).show();
            return;
        }
        String mobile = customerPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this,getString(R.string.text_must_input_contact_phone),Toast.LENGTH_SHORT).show();
            return;
        }
        if (!PhoneUtil.isMobileExact(mobile)) {
            Toast.makeText(this,getString(R.string.text_mobile_syntax_error),Toast.LENGTH_SHORT).show();
            customerPhoneEditText.setText(getString(R.string.text_empty));
            return;
        }
        String relation = customerWithContactTextView.getText().toString();
        if (TextUtils.isEmpty(relation)) {
            Toast.makeText(this,getString(R.string.text_must_input_relation),Toast.LENGTH_SHORT).show();
            return;
        }
        String careIntent = customerCareIntentTextView.getText().toString();
        if (TextUtils.isEmpty(careIntent)) {
            Toast.makeText(this,getString(R.string.text_must_input_care_intent),Toast.LENGTH_SHORT).show();
            return;
        }

        int sexNumber = InputAttendantInfoActivity.SEX_MAN;
        if (sex.equals(getResources().getStringArray(R.array.array_sex)[InputAttendantInfoActivity.SEX_MAN - 1])) {
            sexNumber = InputAttendantInfoActivity.SEX_MAN;
        }else if (sex.equals(getResources().getStringArray(R.array.array_sex)[InputAttendantInfoActivity.SEX_WOMAN - 1])) {
            sexNumber = InputAttendantInfoActivity.SEX_WOMAN;
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
        Call<Customer> call = recommendService.createCustomer(String.format("%s %s",getString(R.string.text_prefix_token),
                token),new Customer(name,sexNumber,birthday,contact,mobile,relation,careIntent));
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getId() == 0) {
                        Toast.makeText(InputCustomerInfoActivity.this,getString(R.string.text_recommend_customer_failed),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InputCustomerInfoActivity.this,getString(R.string.text_recommend_customer_succeed),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(InputCustomerInfoActivity.this,getString(R.string.text_recommend_customer_failed),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputCustomerInfoActivity.this,getString(R.string.text_recommend_customer_failed),Toast.LENGTH_SHORT).show();
            }
        });

    }


    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataStr = format.format(date);
        return dataStr;
    }

    public void back(View view) {
        finish();
    }

}
