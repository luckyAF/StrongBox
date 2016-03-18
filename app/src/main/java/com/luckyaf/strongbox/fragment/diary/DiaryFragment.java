package com.luckyaf.strongbox.fragment.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：日记
 *
 * @auhter. luckyAF
 * 16/3/18
 */
public class DiaryFragment extends BaseFragment {

    private final String fragmentName = "DiaryFragment(日记本)";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_diary, container, false);
    }

    public static DiaryFragment newInstance(Bundle args) {
        DiaryFragment diaryFragment = new DiaryFragment();
        diaryFragment.setArguments(args);
        return diaryFragment;
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
