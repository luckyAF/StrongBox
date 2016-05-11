package com.luckyaf.strongbox.fragment.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：设置
 *
 * @auhter. luckyAF
 * 16/3/18
 */
public class SettingsFragment extends BaseFragment{
    private final String fragmentName = "SettingsFragment(设置)";
    private static BaseSettingsFragment baseSettingsFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public static SettingsFragment newInstance(Bundle args) {
        SettingsFragment settingsFragment = new SettingsFragment();
        baseSettingsFragment = new BaseSettingsFragment();

        return settingsFragment;
    }




    public void initWidget(View view) {

    }

    public void initData() {

    }

    public void initListener() {

    }

    public String getFragmentName() {
        return fragmentName;
    }

    public void onClick(View v) {

    }
}
