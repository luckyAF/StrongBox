package com.luckyaf.strongbox.fragment.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.fragment.BaseFragment;

/**
 * 类描述：视频
 *
 * @auhter Created by luckyAF on 16/3/19
 */
public class VideoFragment extends BaseFragment{
    private final String fragmentName = "ImageFragment(加密视频)";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_file_video, container, false);
    }

    public static VideoFragment newInstance(Bundle args) {
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(args);
        return videoFragment;
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
