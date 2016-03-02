package com.luckyaf.strongbox.util;

import android.content.Context;
import android.widget.Toast;

import com.luckyaf.strongbox.R;


/**
 * Toast工具类
 */
public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showMessage(Context context, CharSequence message) {
        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showMessage(Context context, int message) {
        if (isShow) {
            Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     */
    public static void showNetErrorMessage(Context context) {
        if (isShow) {
            Toast.makeText(context, context.getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
    }
}
