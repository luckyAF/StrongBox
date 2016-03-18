package com.luckyaf.strongbox.fragment.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：文件
 *
 * @auther XCF
 */
public  class FileMainFragment extends BaseFragment {

    private final String fragmentName = "FileMainFragment(加密文件)";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

    public static FileMainFragment newInstance(Bundle args) {
        FileMainFragment fileMainFragment = new FileMainFragment();
        fileMainFragment.setArguments(args);
        return fileMainFragment;
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
    public void onClick(View view) {

    }
}
