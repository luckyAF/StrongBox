package com.luckyaf.strongbox.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.luckyaf.strongbox.service.ProgramProtectService;
import com.luckyaf.strongbox.util.Constant;
import com.luckyaf.strongbox.util.SPUtils;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/5/22
 */
public class ProtectStartUp extends BroadcastReceiver {
    //开机启动
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            boolean IsProtectOn = (Boolean) SPUtils.get(context, Constant.SP_LOCK_PROGRAM, false);
            if (IsProtectOn) {
                context.startService(new Intent(context, ProgramProtectService.class));
            } else {
                context.stopService(new Intent(context, ProgramProtectService.class));
            }
        }

    }
}
