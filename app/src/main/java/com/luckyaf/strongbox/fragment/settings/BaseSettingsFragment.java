package com.luckyaf.strongbox.fragment.settings;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/5/11
 */
public class BaseSettingsFragment extends PreferenceFragment {
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        return super.onPreferenceTreeClick(preferenceScreen,preference);
    }
}
