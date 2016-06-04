package com.luckyaf.strongbox.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 类描述：tabLayout适配器
 *
 * @author Created by luckyAF on 16/3/19
 */
public class TabLayoutAdapter extends FragmentPagerAdapter{
    private List<Fragment> _listFragment;
    private List<String> _listTitle;

    public TabLayoutAdapter(FragmentManager fragmentManager,List<Fragment> listFragment,List<String> listTitle){
        super(fragmentManager);
        _listFragment = listFragment;
        _listTitle = listTitle;
    }
    @Override
    public Fragment getItem(int position) {
        return _listFragment.get(position % _listTitle.size());
    }

    @Override
    public int getCount() {
        return _listTitle.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return _listTitle.get(position % _listTitle.size());
    }
}
