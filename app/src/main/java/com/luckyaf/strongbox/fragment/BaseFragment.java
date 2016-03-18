package com.luckyaf.strongbox.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luckyaf.strongbox.R;


/**
 * 类描述
 *
 * @author Created by Xcf on 2016/2/24.
 * @FileName BaseFragment.java
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_base, container, false);
    }



    public abstract void initWidget();

    public abstract void initData();

    public abstract void initListener();

    public abstract String getFragmentName();

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }



    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        Log.i(getFragmentName(), "onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        Log.i(getFragmentName(), "onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        Log.i(getFragmentName(),"onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        Log.i(getFragmentName(),"onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        Log.i(getFragmentName(),"onDestroy");
        super.onDestroyView();
    }


}
