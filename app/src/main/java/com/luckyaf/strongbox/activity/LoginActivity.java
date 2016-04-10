package com.luckyaf.strongbox.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = new Intent(getBaseContext(),CalculatorActivity.class);
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
