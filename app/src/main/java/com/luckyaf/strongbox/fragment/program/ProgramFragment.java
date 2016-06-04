package com.luckyaf.strongbox.fragment.program;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.AddLockProgramActivity;
import com.luckyaf.strongbox.activity.EditDiaryActivity;
import com.luckyaf.strongbox.adapter.ProgramAdapter;
import com.luckyaf.strongbox.bean.MyProgram;
import com.luckyaf.strongbox.fragment.BaseFragment;
import com.luckyaf.strongbox.util.AppSettings;
import com.luckyaf.strongbox.util.Constant;

import java.util.ArrayList;
import java.util.List;

import me.luckyaf.greendao.MyApps;

/**
 * 类描述：程序
 *
 * @author. luckyAF
 * 16/3/18
 */
public class ProgramFragment extends BaseFragment{
    private final String fragmentName = "ProgramFragment(加密程序)";
    private Button _btnAdd;
    private TextView tvTip;
    private RecyclerView recyclerView;
    private ArrayList<MyProgram> myPrograms = new ArrayList<>();
    private ArrayList<ResolveInfo> mApps = new ArrayList<>();
    private ArrayList<String> protectedAppStrings = new ArrayList<>();
    private ProgramAdapter programAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    public static ProgramFragment newInstance(Bundle args) {
        ProgramFragment programFragment = new ProgramFragment();
        programFragment.setArguments(args);
        return programFragment;
    }

    @Override
    public void initWidget(View view) {
        _btnAdd = (Button)view.findViewById(R.id.btn_add);
        tvTip = (TextView)view.findViewById(R.id.tv_open_lock);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_program);
        programAdapter = new ProgramAdapter(getActivity(),myPrograms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(programAdapter);
    }

    @Override
    public void initData() {
        if(AppSettings.getLockProgram()){
            tvTip.setVisibility(View.INVISIBLE);
        }else{
            tvTip.setVisibility(View.VISIBLE);
        }
        protectedAppStrings.clear();
        mApps.clear();
        myPrograms.clear();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps.addAll(getActivity().getPackageManager().queryIntentActivities(mainIntent, 0));
        List<MyApps> protectedApps = MyApplication.daoMaster.newSession().getMyAppsDao().loadAll();
        if(protectedApps != null && protectedApps.size() > 0 ){
            for(int i = 0; i < protectedApps.size(); i ++)
                protectedAppStrings.add(protectedApps.get(i).getPackageName());
        }
        for(ResolveInfo resolveInfo :mApps){
            MyProgram newProgram = new MyProgram(resolveInfo,true);
            if(protectedAppStrings.size() != 0){
                if(protectedAppStrings.contains(newProgram.getResolveInfo().activityInfo.packageName)){
                    myPrograms.add(newProgram);
                }
            }

        }
        programAdapter.notifyDataSetChanged();


    }

    @Override
    public void initListener() {
        _btnAdd.setOnClickListener(this);
    }

    @Override
    public String getFragmentName() {
        return fragmentName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(resultCode == Activity.RESULT_OK){
            initData();
        //}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Intent intent = new Intent(getContext(), AddLockProgramActivity.class);
                startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_DIARY);
                break;
            default:break;
        }
    }
}
