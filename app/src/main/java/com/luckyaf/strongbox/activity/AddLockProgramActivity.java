package com.luckyaf.strongbox.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseSwipeBackActivity;
import com.luckyaf.strongbox.adapter.ProgramAdapter;
import com.luckyaf.strongbox.bean.MyProgram;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @auhter Created by luckyAF on 16/4/27
 */
public class AddLockProgramActivity extends BaseSwipeBackActivity{
    private Toolbar mToolbar;
    private MenuItem menuItem;
    private ArrayList<ResolveInfo> mApps = new ArrayList<>();
    private ArrayList<MyProgram> myPrograms = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ProgramAdapter programAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lock_program);
        initWidget();
        initData();
        initListener();
    }


    public void initWidget(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        initToolBar(mToolbar);
        programAdapter = new ProgramAdapter(this,myPrograms);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_program);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(programAdapter);
    }
    public void initData(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps.addAll(this.getPackageManager().queryIntentActivities(mainIntent, 0));
        for(ResolveInfo resolveInfo :mApps){
            MyProgram newProgram = new MyProgram(resolveInfo,false);
            myPrograms.add(newProgram);
        }

        programAdapter.notifyDataSetChanged();



    }
    public void initListener(){

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_lock_program;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void onClick(View v) {

    }
}
