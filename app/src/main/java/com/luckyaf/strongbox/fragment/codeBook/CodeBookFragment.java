package com.luckyaf.strongbox.fragment.codeBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述： 密码本
 * Created by luckyAF on 16/3/18.
 */
public class CodeBookFragment extends BaseFragment{
    private final String fragmentName = "CodeBookFragment(密码本)";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_code_book, container, false);
    }

    public static CodeBookFragment newInstance(Bundle args) {
        CodeBookFragment codeBookFragment = new CodeBookFragment();
        codeBookFragment.setArguments(args);
        return codeBookFragment;
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
