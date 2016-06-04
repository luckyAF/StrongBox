package com.luckyaf.strongbox.fragment.settings;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.service.ProgramProtectService;
import com.luckyaf.strongbox.util.AppSettings;
import com.luckyaf.strongbox.util.Constant;
import com.luckyaf.strongbox.util.SPUtils;
import com.luckyaf.strongbox.util.SettingsUtils;
import com.luckyaf.strongbox.util.ToastUtils;

import java.security.Permission;

/**
 * 类描述：设置页面
 *
 * @author Created by luckyAF on 16/5/11
 */
public class BaseSettingsFragment extends PreferenceFragment {
    private CheckBoxPreference openProgramLock;
    private CheckBoxPreference codeBookSettings;
    private Boolean isLock;
    private Boolean isShow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_settings);
        getPreferenceManager().setSharedPreferencesName(SPUtils.FILE_NAME);
        initWidget();
        initData();
    }

    public void initWidget() {
        openProgramLock = (CheckBoxPreference) findPreference(getString(R.string.key_program_lock));
        codeBookSettings = (CheckBoxPreference) findPreference(getString(R.string.key_code_book));
    }

    public void initData() {
        isLock = AppSettings.getLockProgram();
        isShow = AppSettings.getShowPassword();
        openProgramLock.setChecked(isLock);
        codeBookSettings.setChecked(isShow);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String key = preference.getKey();
        if (TextUtils.equals(key, getString(R.string.key_program_lock))) {
            //程序锁
            isLock = !isLock;
            if (isLock) {
                getActivity().startService(new Intent(getActivity(), ProgramProtectService.class));
            } else {
                getActivity().stopService(new Intent(getActivity(), ProgramProtectService.class));
            }
            AppSettings.setLockProgram(getActivity(), isLock);

        } else if (TextUtils.equals(key, getString(R.string.key_code_book))) {
            isShow = !isShow;
            AppSettings.setShowPassword(getActivity(), isShow);

        } else if (TextUtils.equals(key, getString(R.string.key_login_code))) {
            changeDialog();
        } else if (TextUtils.equals(key, getString(R.string.key_lock_permission))) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }


    public void changeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("修改登录密码");
        final View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        builder.setView(viewDialog);


        builder.setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText etOldPassword = (EditText) viewDialog.findViewById(R.id.et_old_password);
                EditText etNewPassword = (EditText) viewDialog.findViewById(R.id.et_new_password);
                EditText etConfirmPassword = (EditText) viewDialog.findViewById(R.id.et_confirm_password);

                int oldPassword = -1;
                if (etOldPassword.getText().toString().trim().length() > 0) {
                    oldPassword = Integer.parseInt(etOldPassword.getText().toString().trim());
                }

                int password = AppSettings.getLoginNumber();
                if (oldPassword == password
                        && etNewPassword.getText().toString().equals(etConfirmPassword.getText().toString())
                        && etNewPassword.getText().toString().length() != 0) {
                    AppSettings.setLoginNumber(getActivity(), Integer.parseInt(etNewPassword.getText().toString().toString()));
                    Snackbar.make(getView(), "修改成功", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(getView(), "出现错误", Snackbar.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.common_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public Boolean ifPermissionOn() {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = getActivity().getPackageManager();
            boolean permission = (PackageManager.PERMISSION_GRANTED ==
                    pm.checkPermission(Settings.ACTION_USAGE_ACCESS_SETTINGS, "com.luckyaf.strongbox"));
            return permission;

        } else {
            return true;
        }
    }

}
