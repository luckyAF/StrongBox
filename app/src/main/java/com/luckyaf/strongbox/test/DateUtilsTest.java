package com.luckyaf.strongbox.test;

import android.provider.Settings;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.luckyaf.strongbox.util.DateUtils;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/10
 */
public class DateUtilsTest {
    public static void main(String[] args){
        System.out.println(DateUtils.TimeMills2Date(DateUtils.currentTime2String()));
        System.out.println(DateUtils.TimeMills2Date(DateUtils.currentTime2String()));
        System.out.println(DateUtils.currentTime2String());
        System.out.println(DateUtils.currentTime2String());
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());

    }
}
