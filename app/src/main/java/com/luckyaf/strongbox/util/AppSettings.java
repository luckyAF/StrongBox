package com.luckyaf.strongbox.util;

import android.content.Context;

import java.io.File;
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
 * @author Created by luckyAF on 16/4/16
 */
public class AppSettings {
    public static int LoginNumber;//登录密码
    public static Boolean ShowPassword;//是否显示密码
    public static String AESKey;//加密密码
    public static Boolean FirstUse;//是否是第一次使用
    public static Boolean lockProgram;//是否 加锁程序
    public static Boolean randomBoard;// 是否随机输入面板
    public static String myDir;//应用目录


    public static void initSettings(Context context){
        LoginNumber = (Integer)SPUtils.get(context,Constant.SP_LOGIN_NUMBER,123456);
        AESKey = (String)SPUtils.get(context,Constant.SP_AES_KEY,"luckyAFBoxAESKEY");
        ShowPassword = (Boolean)SPUtils.get(context,Constant.SP_SHOW_PASSWORD,false);
        FirstUse = (Boolean)SPUtils.get(context,Constant.SP_FIRST_USE,true);
        lockProgram = (Boolean)SPUtils.get(context,Constant.SP_LOCK_PROGRAM,false);
        randomBoard = (Boolean)SPUtils.get(context,Constant.SP_RANDOM_BOARD,false);
        myDir = (String)SPUtils.get(context,Constant.SP_STRONG_BOX_DIR,context.getFilesDir().getPath()+ File.separator+"StrongBonData");
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

    public static Boolean getLockProgram() {
        return lockProgram;
    }

    public static void setLockProgram(Context context,Boolean lockProgram) {
        AppSettings.lockProgram = lockProgram;
        SPUtils.put(context,Constant.SP_LOCK_PROGRAM,lockProgram);
    }

    public static Boolean getRandomBoard() {
        return randomBoard;
    }

    public static void setRandomBoard(Context context,Boolean randomBoard) {
        AppSettings.randomBoard = randomBoard;
        SPUtils.put(context,Constant.SP_RANDOM_BOARD,randomBoard);
    }

    public static String getMyDir() {
        return myDir;
    }

    public static void setMyDir(Context context,String myDir) {
        AppSettings.myDir = myDir;
        SPUtils.put(context,Constant.SP_STRONG_BOX_DIR,AppSettings.myDir);
    }


}
