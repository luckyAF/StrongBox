package com.luckyaf.strongbox.test;

import com.luckyaf.strongbox.util.DateUtils;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 16/4/10
 */
public class DateUtilsTest {
    public static void main(String[] args){
        System.out.println(DateUtils.TimeMills2Date(DateUtils.currentTime2String()));
    }
}
