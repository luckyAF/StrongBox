package com.luckyaf.strongbox.fragment.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：文档
 *
 * @author Created by luckyAF on 16/3/19
 */
public class DocumentFragment extends BaseFragment{

    private final String fragmentName = "DocumentFragment(加密文档)";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_file_document, container, false);
    }

    public static DocumentFragment newInstance(Bundle args) {
        DocumentFragment documentFragment = new DocumentFragment();
        documentFragment.setArguments(args);
        return documentFragment;
    }
    @Override
    public void initWidget(View view) {

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
