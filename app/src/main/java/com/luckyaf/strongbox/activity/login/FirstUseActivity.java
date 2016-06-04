package com.luckyaf.strongbox.activity.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.MainActivity;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.util.AppSettings;
import com.luckyaf.strongbox.util.ToastUtils;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/17
 */
public class FirstUseActivity extends BaseActivity{
    private EditText etPassword;
    private EditText etConfirm;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);
        initWidget();
        initData();
        initListener();

    }

    public void initWidget(){
        etPassword = (EditText)findViewById(R.id.et_passWord);
        etConfirm = (EditText)findViewById(R.id.et_confirm);
        btnConfirm = (Button)findViewById(R.id.btn_confirm);
    }

    public void initData(){

    }

    public void initListener(){
        btnConfirm.setOnClickListener(this);
    }

    public void savePassword(){
        AppSettings.setFirstUse(this,false);
        AppSettings.setLoginNumber(this,Integer.valueOf(etPassword.getText().toString().trim()));
    }

    public void dealConfirm(){
        String a = etPassword.getText().toString().trim();
        String b = etConfirm.getText().toString().trim();
        if(a.length() == 0 && b.length() == 0){
            ToastUtils.showMessage(this,getString(R.string.can_not_be_null));
            return;
        }
        if(a.equals(b)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.common_tip));
            builder.setMessage(getString(R.string.alert_remember_password));//
            builder.setPositiveButton(getString(R.string.remember_already), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    savePassword();
                    dialog.dismiss();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton(getString(R.string.remember_again), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else{
            ToastUtils.showMessage(this,getString(R.string.error_not_same));
            etPassword.setText(null);
            etConfirm.setText(null);
        }
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
        switch (v.getId()){
            case R.id.btn_confirm:
                dealConfirm();
                break;
            default:
                break;
        }
    }
}
