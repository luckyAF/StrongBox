package com.luckyaf.strongbox.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.util.ScreenUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 类描述：activity基类
 *
 * @auhter Created by luckyAF on 16/3/30
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    private SystemBarTintManager tintManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明导航栏
       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initToolbar();
        initWindow();
    }


    protected void initToolBar(Toolbar toolbar) {
        if (toolbar == null) return;
        toolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(toolbar);
       /* if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
    }

    @TargetApi(19)
    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    protected abstract void initToolbar();

    protected abstract boolean isApplyTranslucency();

}
