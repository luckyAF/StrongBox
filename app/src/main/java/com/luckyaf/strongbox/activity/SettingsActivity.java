package com.luckyaf.strongbox.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.fragment.settings.BaseSettingsFragment;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/5/11
 */
public class SettingsActivity extends BaseActivity {

    private Toolbar _mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);_mToolbar = (Toolbar) findViewById(R.id.common_toolbar);

        initToolBar(_mToolbar);
        _mToolbar.setTitle(getString(R.string.menu_settings));
        setFragment();

    }

    @Override

    protected void initToolbar() {

    }


    private void setFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new BaseSettingsFragment())
                .commit();
    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
