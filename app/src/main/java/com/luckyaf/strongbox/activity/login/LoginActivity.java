package com.luckyaf.strongbox.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.util.AppSettings;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent;
        if(AppSettings.getFirstUse()){
            intent = new Intent(getBaseContext(),FirstUseActivity.class);
        }else{
            intent = new Intent(getBaseContext(),CalculatorActivity.class);
        }

        startActivity(intent);
        finish();
    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
