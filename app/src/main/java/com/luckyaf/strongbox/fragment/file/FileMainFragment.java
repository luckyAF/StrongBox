package com.luckyaf.strongbox.fragment.file;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.adapter.TabLayoutAdapter;
import com.luckyaf.strongbox.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类描述：文件
 *
 * @auther XCF
 */
public  class FileMainFragment extends BaseFragment {

    private final String fragmentName = "FileMainFragment(加密文件)";
    private TabLayout _tabFileFragmentTitle;
    private ViewPager _vpFileFragmentPager;
    private FragmentPagerAdapter _fragmentPagerAdapter;             //    适配器
    private List<Fragment> _listFragment;                           //    fragment 列表
    private List<String> _listTitle;                                //     标题 列表
    private ImageFragment _imageFragment;                           //     图片
    private VideoFragment _videoFragment;                           //     视频
    private AudioFragment _audioFragment;                           //     音频
    private DocumentFragment _documentFragment;                     //     文档




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static FileMainFragment newInstance(Bundle args) {
        FileMainFragment fileMainFragment = new FileMainFragment();
        fileMainFragment.setArguments(args);
        return fileMainFragment;
    }

    @Override
    public void initWidget(View view) {
        _tabFileFragmentTitle = (TabLayout)view.findViewById(R.id.tab_file_fragment_title);
        _vpFileFragmentPager = (ViewPager)view.findViewById(R.id.vp_file_fragment_pager);
        Bundle bundle = new Bundle();
        _listFragment = new ArrayList<>();
        _imageFragment = ImageFragment.newInstance(bundle);
        _videoFragment = VideoFragment.newInstance(bundle);
        _audioFragment = AudioFragment.newInstance(bundle);
        _documentFragment = DocumentFragment.newInstance(bundle);
        _listFragment.add(_imageFragment);
        _listFragment.add(_videoFragment);
        _listFragment.add(_audioFragment);
        _listFragment.add(_documentFragment);

        String[] titles = getResources().getStringArray(R.array.file_type);
        _listTitle = new ArrayList<>();
        _listTitle.addAll(Arrays.asList(titles));

        _tabFileFragmentTitle.setTabMode(TabLayout.MODE_FIXED);
        _tabFileFragmentTitle.addTab(_tabFileFragmentTitle.newTab().setText(_listTitle.get(0)));
        _tabFileFragmentTitle.addTab(_tabFileFragmentTitle.newTab().setText(_listTitle.get(1)));
        _tabFileFragmentTitle.addTab(_tabFileFragmentTitle.newTab().setText(_listTitle.get(2)));
        _tabFileFragmentTitle.addTab(_tabFileFragmentTitle.newTab().setText(_listTitle.get(3)));

        _fragmentPagerAdapter = new TabLayoutAdapter(getActivity().getSupportFragmentManager(),_listFragment,_listTitle);
        _vpFileFragmentPager.setAdapter(_fragmentPagerAdapter);
        _vpFileFragmentPager.setOffscreenPageLimit(3);
        _tabFileFragmentTitle.setupWithViewPager(_vpFileFragmentPager);
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
