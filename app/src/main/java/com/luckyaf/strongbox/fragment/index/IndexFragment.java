package com.luckyaf.strongbox.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：首页
 *
 * @author XCF
 */
public class IndexFragment extends BaseFragment {
    private final String fragmentName = "IndexFragment(首页)";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    public static IndexFragment newInstance(Bundle args) {
        IndexFragment indexFragment = new IndexFragment();
        indexFragment.setArguments(args);
        return indexFragment;
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
    public void onClick(View view) {

    }
}
