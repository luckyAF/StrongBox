package com.luckyaf.strongbox.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/9
 */
public class MyAlertDialog {
    public static void showDialog(String msg, String positiveMsg, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(msg);//
      //  builder.setPositiveButton(positiveMsg, mEditImpl);
      //  builder.setNegativeButton("取消", mEditImpl);
       // alertDialog = builder.show();
    }
}
