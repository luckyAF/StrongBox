package com.luckyaf.strongbox.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.activity.AppLockActivity;
import com.luckyaf.strongbox.util.AppSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import me.luckyaf.greendao.DaoSession;
import me.luckyaf.greendao.MyApps;

/**
 * 类描述：加锁应用程序服务
 *
 * @author Created by luckyAF on 16/4/28
 */
public class ProgramProtectService extends Service implements View.OnClickListener{
    static public ActivityManager mActivityManager;
    private DaoSession mDaoSession;
    private ArrayList<MyApps> myApps = new ArrayList<>();
    private List<ActivityManager.RunningTaskInfo> mRunningTasks;
    private ArrayList<String> packagesProtected = new ArrayList<>();
    private ArrayList<String> classProtected = new ArrayList<>();
    private ArrayList<String> arrayRunningApps = new ArrayList<>();
    private ArrayList<String> aryRunningClass = new ArrayList<>();
    private View mLockView;
    private EditText mEditText;
    private String input;
    private WindowManager mWindowManager = null;
    private Context mContext;
    private Button[][] buttons;
    private String[] buttonTexts = {"1","2","3","4","5","6","7","8","9","0","DEL","OK"};
    private String protectClassName;
    private String packageName;
    private String className;
    private Boolean lockFlag;
    private String lastApp;
    private String thisApp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }


    public void initWidget(){
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                PixelFormat.RGBA_8888);
        params.gravity = (Gravity.CENTER);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLockView= layoutInflater.inflate(R.layout.service_lock_app, null);
        mEditText = (EditText)mLockView.findViewById(R.id.et_password);
        buttons = new Button[4][3];
        initButton();
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        if (mWindowManager != null && mLockView!= null) {
            mWindowManager.addView(mLockView, params);
            lastApp = thisApp;
            lockFlag = false;
        }
    }

    public void initButton(){
        buttons[0][0] = (Button)mLockView.findViewById(R.id.btn_00);
        buttons[0][1] = (Button)mLockView.findViewById(R.id.btn_01);
        buttons[0][2] = (Button)mLockView.findViewById(R.id.btn_02);
        buttons[1][0] = (Button)mLockView.findViewById(R.id.btn_10);
        buttons[1][1] = (Button)mLockView.findViewById(R.id.btn_11);
        buttons[1][2] = (Button)mLockView.findViewById(R.id.btn_12);
        buttons[2][0] = (Button)mLockView.findViewById(R.id.btn_20);
        buttons[2][1] = (Button)mLockView.findViewById(R.id.btn_21);
        buttons[2][2] = (Button)mLockView.findViewById(R.id.btn_22);
        buttons[3][0] = (Button)mLockView.findViewById(R.id.btn_30);
        buttons[3][1] = (Button)mLockView.findViewById(R.id.btn_31);
        buttons[3][2] = (Button)mLockView.findViewById(R.id.btn_32);
        List list = Arrays.asList(buttonTexts);
        if(AppSettings.getRandomBoard()){
            Collections.shuffle(list);
        }
        for(int i = 0; i < 4; i ++){
            for(int j = 0; j < 3; j ++){
                buttons[i][j].setText((String)list.get(i * 3 + j));
                buttons[i][j].setOnClickListener(this);
            }
        }

    }

    public void initData(){
        if(!AppSettings.getLockProgram()){
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        mContext = getApplicationContext();
        mDaoSession = MyApplication.daoMaster.newSession();
        myApps.clear();
        packagesProtected.clear();
        if(mDaoSession.getMyAppsDao().loadAll() != null && mDaoSession.getMyAppsDao().loadAll().size() != 0){
            myApps.addAll(mDaoSession.getMyAppsDao().loadAll());
        }

        for(int i = 0; i < myApps.size(); i ++){
            //if(myApps.get(i).getLock()){
                packagesProtected.add(myApps.get(i).getPackageName());
                classProtected.add(myApps.get(i).getClassName());
            //}
        }
        protectClassName = new String();
        lastApp = new String();
        thisApp = new String();
        lockFlag =true;
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        //主要的监听保护应用程序的任务还是交给主线程里面的子线程处理

        new Thread(new Runnable() {
            public void run()//实现监听被保护的应用程序
            {
                Looper.prepare();
                while (true) {
                    mRunningTasks = mActivityManager.getRunningTasks(15);

                    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        /*List<ActivityManager.RunningAppProcessInfo> tasks = mActivityManager
                                .getRunningAppProcesses();

                        for (ActivityManager.RunningAppProcessInfo processInfo : tasks) {
                            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                packageName = processInfo.pkgList[0];
                                Log.v("StrongBox",processInfo.processName);
                                for(int i = 0; i < processInfo.pkgList.length; i ++){
                                    Log.v("StrongBox",processInfo.pkgList[i]);
                                }
                            }
                        }*/

                        packageName = String.valueOf(System.currentTimeMillis());
                        UsageStatsManager usm = (UsageStatsManager) getApplicationContext().getSystemService(USAGE_STATS_SERVICE);
                        long time = System.currentTimeMillis();
                        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
                        if (appList != null && appList.size() > 0) {
                            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                            for (UsageStats usageStats : appList) {
                                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                            }
                            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                                packageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                            }
                        }
                        Log.v("StrongBox",packageName);
                    }else{
                        List<ActivityManager.RunningTaskInfo> runningTasks = mActivityManager
                                .getRunningTasks(1);
                        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                        ComponentName topActivity = runningTaskInfo.topActivity;
                        if (topActivity != null) {
                            packageName = topActivity.getPackageName();
                            className = topActivity.getClassName();
                        }
                    }
                    thisApp = packageName;

                    if(packagesProtected.size() != 0){
                        if(packagesProtected.contains(packageName)){
                            if(thisApp.equals(lastApp) || lastApp.equals("com.luckyaf.strongbox")){
                                lockFlag = false;
                            }
                            if(lockFlag){
                                Log.d("StrongBox",packageName);
                                //initWidget();
                                Intent intent = new Intent();
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setClassName("com.luckyaf.strongbox","com.luckyaf.strongbox.activity.AppLockActivity");
                                startActivity(intent);
                            }
                        }
                    }
                    lockFlag = true;
                    lastApp = thisApp;
                    try {
                        Thread.sleep(300);//防止无限循环导致的应用程序占用CPU过高 所以加上sleep释放cpu
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        }).start();
        return START_STICKY;
    }


    @Override
    public void onClick(View v) {
        Button button = (Button) mLockView.findViewById(v.getId());
        if(button.getText().equals("DEL")){
            int size = input.length();
            if (size > 0 ) {
                input = input.substring(0, size - 1);
            }
        }else if(button.getText().equals("OK")){
            try {
                int result = Integer.parseInt(input);
                int loginNumber = AppSettings.getLoginNumber();
                if (result == loginNumber){
                    mWindowManager.removeViewImmediate(mLockView);
                }else{
                    input = "";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }else{
            input += button.getText();
        }
        setInput(input);
    }

    public void setInput(String string){
        mEditText.setText(string);
    }
}
