package com.luckyaf.strongbox.util;

import android.content.Context;
import android.os.Vibrator;

/**
 * 类描述：振动
 *
 * @auhter Created by luckyAF on 16/4/1
 */
public class VibrateUtils {
    private static Vibrator vibrator;

    /**
     * 短振动
     *
     */
    public static void vShort(Context context) {
        vSimple(context,100);
    }

    /**
     * 简单震动
     *
     * @param context     调用震动的Context
     * @param millisecond 震动的时间，毫秒
     */
    @SuppressWarnings("static-access")
    public static void vSimple(Context context, int millisecond) {
        vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(millisecond);
    }

    /**
     * 复杂的震动
     *
     * @param context 调用震动的Context
     * @param pattern 震动形式
     * @param repeate 震动的次数，-1不重复，非-1为从pattern的指定下标开始重复
     */
    @SuppressWarnings("static-access")
    public static void vComplicated(Context context, long[] pattern, int repeate) {
        vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeate);
    }

    /**
     * 停止震动
     */
    public static void stop() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
