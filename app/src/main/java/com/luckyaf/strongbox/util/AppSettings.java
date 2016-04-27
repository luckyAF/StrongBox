package com.luckyaf.strongbox.util;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 类描述：应用的设置
 *
 * @auhter Created by luckyAF on 16/4/16
 */
public class AppSettings {
    public static int LoginNumber;
    public static Boolean ShowPassword;
    public static String AESKey;
    public static Boolean FirstUse;



    public static void initSettings(Context context){
        LoginNumber = (Integer)SPUtils.get(context,Constant.SP_LOGIN_NUMBER,123456);
        AESKey = (String)SPUtils.get(context,Constant.SP_AES_KEY,"StrongBoxAESKEY");
        ShowPassword = (Boolean)SPUtils.get(context,Constant.SP_SHOW_PASSWORD,false);
        FirstUse = (Boolean)SPUtils.get(context,Constant.SP_FIRST_USE,true);
    }

    public static int getLoginNumber() {
        return LoginNumber;
    }

    public static void setLoginNumber(Context context,int loginNumber) {
        LoginNumber = loginNumber;
        SPUtils.put(context,Constant.SP_LOGIN_NUMBER,LoginNumber);
    }

    public static Boolean getShowPassword() {
        return ShowPassword;
    }

    public static void setShowPassword(Context context,Boolean showPassword) {
        ShowPassword = showPassword;
        SPUtils.put(context,Constant.SP_SHOW_PASSWORD,ShowPassword);
    }

    public static String getAESKey() {
        return AESKey;
    }

    public static void setAESKey(Context context,String AESKey) {
        AppSettings.AESKey = AESKey;
        SPUtils.put(context,Constant.SP_AES_KEY,AESKey);
    }

    public static Boolean getFirstUse() {
        return FirstUse;
    }

    public static void setFirstUse(Context context,Boolean firstUse) {
        FirstUse = firstUse;
        SPUtils.put(context,Constant.SP_FIRST_USE,FirstUse);
    }


}
