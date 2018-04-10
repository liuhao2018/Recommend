package com.limefamily.recommend.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.limefamily.recommend.R;

public class AccountSecureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_secure);
        View changePassword = findViewById(R.id.rl_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSecureActivity.this,PasswordManageActivity.class));
            }
        });
    }

    public void back(View view) {
        finish();
    }
}
