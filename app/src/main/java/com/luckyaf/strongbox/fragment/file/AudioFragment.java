package com.luckyaf.strongbox.fragment.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：音频
 *
 * @author Created by luckyAF on 16/3/19
 */
public class AudioFragment extends BaseFragment{

    private final String fragmentName = "ImageFragment(加密音频)";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_file_audio, container, false);
    }

    public static AudioFragment newInstance(Bundle args) {
        AudioFragment audioFragment = new AudioFragment();
        audioFragment.setArguments(args);
        return audioFragment;
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
