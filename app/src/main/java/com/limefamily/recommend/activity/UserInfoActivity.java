package com.limefamily.recommend.activity;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.model.User;
import com.limefamily.recommend.restful.UserService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserInfoActivity extends TakePhotoActivity implements View.OnClickListener {

    private TextView userNameTextView,userSexTextView,userBirthdayTextView;
    private SimpleDraweeView userHeadImageView;
    private TimePickerView timePickerView;
    private ProgressBar progressBar;

    private TakePhoto takePhoto;
    private final String FILE_PATH_PREFIX = "file://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        fetchUserInfo();
    }

    private void initView() {
        userNameTextView = findViewById(R.id.tv_user_name);
        userHeadImageView = findViewById(R.id.iv_user_head);
        userSexTextView = findViewById(R.id.tv_user_sex);
        userBirthdayTextView = findViewById(R.id.tv_user_birthday);
        progressBar = findViewById(R.id.pb);
        View userHeadView = findViewById(R.id.rl_user_head);
        View userNameView = findViewById(R.id.rl_user_name);
        View userSexView = findViewById(R.id.rl_user_sex);
        View userBirthdayView = findViewById(R.id.rl_user_birthday);
        userHeadView.setOnClickListener(this);
        userNameView.setOnClickListener(this);
        userSexView.setOnClickListener(this);
        userBirthdayView.setOnClickListener(this);
    }

    private void fetchUserInfo() {
        progressBar.setVisibility(View.VISIBLE);
        String token =RecommendApplication.getInstance().getToken();
        if(TextUtils.isEmpty(token)) {
            Toast.makeText(UserInfoActivity.this,getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.view(String.format("%s %s",getString(R.string.text_prefix_token),token));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        User user = response.body();
                        updateView(user);
                    }else {
                        Toast.makeText(UserInfoActivity.this,getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UserInfoActivity.this,getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(UserInfoActivity.this,getString(R.string.text_fetch_user_info_failed),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateView(User user) {
        if (!TextUtils.isEmpty(user.getAvatar())) {
            userHeadImageView.setImageURI(user.getAvatar());
        }else {
            userHeadImageView.setImageResource(R.mipmap.icon_user_head_default);
        }
        if (!TextUtils.isEmpty(user.getName())) {
            userNameTextView.setText(user.getName());
        }
        if (user.getSex() == 1) {
            userSexTextView.setText(getString(R.string.text_sex_man));
        }else if (user.getSex() == 2) {
            userSexTextView.setText(getString(R.string.text_sex_woman));
        }else {
            userSexTextView.setText(getString(R.string.text_empty));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_head:
                handleSelectUserHead();
                break;
            case R.id.rl_user_name:
                handleInputUserName();
                break;
            case R.id.rl_user_sex:
                handleInputUserSex();
                break;
            case R.id.rl_user_birthday:
                handleInputUserBirthday();
                break;
        }
    }

    /**
     * 选择头像
     */
    private void handleSelectUserHead() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_user_head)
                .items(R.array.array_user_head)
                .itemsCallbackSingleChoice(
                        0,new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                                if (!file.getParentFile().exists()) {
                                    file.getParentFile().mkdirs();
                                }
                                Uri imageUri = Uri.fromFile(file);
                                if (text.equals(UserInfoActivity.this.getResources().getStringArray(R.array.array_user_head)[0])) {
                                    getTakePhoto().onPickFromCapture(imageUri);
                                } else if (text.equals(UserInfoActivity.this.getResources().getStringArray(R.array.array_user_head)[1])){
                                    getTakePhoto().onPickMultiple(1);
                                }
                                return true;
                            }
                        }).positiveText(R.string.text_confirm)
                .build()
                .show();
    }

    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        userHeadImageView.setImageURI(String.format("%s%s",FILE_PATH_PREFIX,result.getImage().getOriginalPath()));
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Toast.makeText(this,getResources().getString(R.string.text_set_use_head_failed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        Toast.makeText(this,getResources().getString(R.string.text_set_use_head_failed),Toast.LENGTH_SHORT).show();
    }

    /**
     * 填写名字
     */
    private void handleInputUserName() {
            new MaterialDialog.Builder(this)
                    .title(R.string.text_input)
                    .content(R.string.text_input_content)
                    .inputType(
                            InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                    | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                    .inputRange(2, 16)
                    .positiveText(R.string.text_submit)
                    .input(null,null,
                            false, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                    userNameTextView.setText(input.toString());
                                    Toast.makeText(UserInfoActivity.this,input.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }).build().show();
    }

    /**
     * 填写性别
     */
    private void handleInputUserSex() {
        new MaterialDialog.Builder(this)
                .title(R.string.text_user_sex)
                .items(R.array.array_sex)
                .itemsCallbackSingleChoice(
                        0,new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                userSexTextView.setText(text);
                                return true;
                            }
                        }).positiveText(R.string.text_submit)
                .build()
                .show();
    }

    private void handleInputUserBirthday() {
       showSelectDate();
    }

    /**
     * 选择出身日期
     */
    private void showSelectDate() {
        if (timePickerView == null) {
            timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    if (date != null) {
                        String birthday = getTime(date);
                        if (!TextUtils.isEmpty(birthday)) {
                            userBirthdayTextView.setText(birthday);
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

    public void back(View view) {
        finish();
    }
}
