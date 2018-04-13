package com.limefamily.recommend.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;

public class AccountSecureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_secure);
        View changePassword = findViewById(R.id.rl_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = RecommendApplication.getInstance().getToken();
                if(TextUtils.isEmpty(token)) {
                    Toast.makeText(AccountSecureActivity.this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                startActivity(new Intent(AccountSecureActivity.this,PasswordManageActivity.class));
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
