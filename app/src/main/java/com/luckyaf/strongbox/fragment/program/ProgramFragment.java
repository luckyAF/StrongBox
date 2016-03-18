package com.luckyaf.strongbox.fragment.program;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：程序
 *
 * @auhter. luckyAF
 * 16/3/18
 */
public class ProgramFragment extends BaseFragment{
    private final String fragmentName = "ProgramFragment(加密程序)";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_program, container, false);
    }

    public static ProgramFragment newInstance(Bundle args) {
        ProgramFragment programFragment = new ProgramFragment();
        programFragment.setArguments(args);
        return programFragment;
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
