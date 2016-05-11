package com.luckyaf.strongbox.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.luckyaf.strongbox.MyApplication;
import com.luckyaf.strongbox.R;
import com.luckyaf.strongbox.util.AppSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.luckyaf.greendao.DaoSession;
import me.luckyaf.greendao.MyApps;

/**
 * 类描述：加锁应用程序服务
 *
 * @auhter Created by luckyAF on 16/4/28
 */
public class ProgramProtectService extends Service implements View.OnClickListener{
    static public ActivityManager mActivityManager;
    private DaoSession mDaoSession;
    private ArrayList<MyApps> myApps = new ArrayList<>();
    private List<ActivityManager.RunningTaskInfo> mRunningTasks;
    private ArrayList<String> packagesProtected = new ArrayList<>();
    private View mLockView;
    private EditText mEditText;
    private String input;
    private WindowManager mWindowManager = null;
    private Context mContext;
    private Button[][] buttons;
    private String[] buttonTexts = {"1","2","3","4","5","6","7","8","9","0","DEL","OK"};

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
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.RGBA_8888);
        params.gravity = (Gravity.CENTER);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mLockView= layoutInflater.inflate(R.layout.service_lock_app, null);
        mEditText = (EditText)mLockView.findViewById(R.id.et_password);

        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }

        if (mWindowManager != null && mLockView!= null) {
            mWindowManager.addView(mLockView, params);
        }
        buttons = new Button[4][3];

        initButton();
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
                buttons[i][j].setText((String)list.get(i * 4 + j));
            }
        }

    }

    public void initData(){
        mContext = getApplicationContext();
        mDaoSession = MyApplication.daoMaster.newSession();
        myApps.clear();
        packagesProtected.clear();
        myApps.addAll(mDaoSession.getMyAppsDao().loadAll());
        for(int i = 0; i < myApps.size(); i ++){
            if(myApps.get(i).getLock()){
                packagesProtected.add(myApps.get(i).getPackageName());
            }
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //主要的监听保护应用程序的任务还是交给主线程里面的子线程处理
        initData();
        new Thread(new Runnable() {
            public void run()//实现监听被保护的应用程序
            {
                while (true) {
                    ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
                    String packageName = topActivity.getPackageName();
                    if(packagesProtected.contains(packageName)){
                        initWidget();
                    }
                    try {
                        Thread.sleep(500);//防止无限循环导致的应用程序占用CPU过高 所以加上sleep释放cpu
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
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
