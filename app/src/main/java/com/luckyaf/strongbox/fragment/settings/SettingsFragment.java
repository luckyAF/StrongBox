package com.luckyaf.strongbox.fragment.settings;

import android.os.Bundle;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public static SettingsFragment newInstance(Bundle args) {
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setArguments(args);
        return settingsFragment;
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public String getFragmentName() {
        return fragmentName;
    }

    @Override
    public void onClick(View v) {

    }
}
