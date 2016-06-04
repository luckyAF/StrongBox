package com.luckyaf.strongbox.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.base.BaseActivity;
import com.luckyaf.strongbox.adapter.ProgramAdapter;
import com.luckyaf.strongbox.bean.MyProgram;

import java.util.ArrayList;
import java.util.List;

import me.luckyaf.greendao.MyApps;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/27
 */
public class AddLockProgramActivity extends BaseActivity{
    private Toolbar mToolbar;
    private MenuItem menuItem;
    private ArrayList<ResolveInfo> mApps = new ArrayList<>();
    private ArrayList<MyProgram> myPrograms = new ArrayList<>();
    private ArrayList<String> protectedAppStrings = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AddLockAdapter programAdapter;
    private Button btnAdd;
    private Button btnCancel;

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
        mToolbar.setTitle(getString(R.string.program_lock_program));
        programAdapter = new AddLockAdapter();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_program);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(programAdapter);
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
    }
    public void initData(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps.addAll(this.getPackageManager().queryIntentActivities(mainIntent, 0));
        List<MyApps> protectedApps = MyApplication.daoMaster.newSession().getMyAppsDao().loadAll();
        if(protectedApps != null && protectedApps.size() > 0 ){
            for(int i = 0; i < protectedApps.size(); i ++)
            protectedAppStrings.add(protectedApps.get(i).getPackageName());
        }
        for(ResolveInfo resolveInfo :mApps){
            MyProgram newProgram = new MyProgram(resolveInfo,false);
            if(protectedAppStrings.size() != 0){
                if(!protectedAppStrings.contains(newProgram.getResolveInfo().activityInfo.packageName)){
                     myPrograms.add(newProgram);
                }
            }else{
                myPrograms.add(newProgram);
            }

        }
        programAdapter.setArray();
        programAdapter.notifyDataSetChanged();



    }
    public void initListener(){
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initToolbar() {

    }

    @Override
    protected boolean isApplyTranslucency() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add:
                for(int i = 0; i < myPrograms.size(); i ++){
                    if(programAdapter.getSelect(i)){
                        MyApps newApp = new MyApps();
                        newApp.setLock(true);
                        newApp.setPackageName(myPrograms.get(i).getResolveInfo().activityInfo.applicationInfo.packageName);
                        newApp.setClassName(myPrograms.get(i).getResolveInfo().activityInfo.applicationInfo.className);
                        MyApplication.daoMaster.newSession().getMyAppsDao().insert(newApp);
                    }
                }
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            default:
                break;

        }
    }

    private class AddLockAdapter extends RecyclerView.Adapter<AddLockAdapter.ViewHolder>{
        Boolean[] arraySelected;
        public void setArray(){
            arraySelected = new Boolean[myPrograms.size()];
            for(int i = 0; i < arraySelected.length; i ++){
                arraySelected[i] = false;
            }
        }
        @Override
        public AddLockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.item_all_app, parent,
                    false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(AddLockAdapter.ViewHolder holder, final int position) {
            holder.imgAppIcon.setImageDrawable(myPrograms.get(position).getResolveInfo().loadIcon(getPackageManager()));
            holder.tvAppName.setText(myPrograms.get(position).getResolveInfo().loadLabel(getPackageManager()));
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v;
                   arraySelected[position] = cb.isChecked();
                }
            });
        }

        @Override
        public int getItemCount() {
            return myPrograms.size();
        }

        public Boolean getSelect(int position){
            return arraySelected[position];

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgAppIcon;
            TextView tvAppName;
            CheckBox checkBox;
            public ViewHolder(View itemView) {
                super(itemView);
                imgAppIcon = (ImageView)itemView.findViewById(R.id.img_app_icon);
                tvAppName = (TextView)itemView.findViewById(R.id.tv_app_name);
                checkBox = (CheckBox)itemView.findViewById(R.id.cb_is_selected);
            }
        }


    }
}
