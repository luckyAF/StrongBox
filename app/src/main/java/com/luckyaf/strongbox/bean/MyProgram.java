package com.luckyaf.strongbox.bean;

import android.content.pm.ResolveInfo;

/**
 * 类描述：程序类  用于存储包含该程序是否被锁信息
 *
 * @auhter Created by luckyAF on 16/4/28
 */
public class MyProgram {
    public Boolean isLock;
    public ResolveInfo resolveInfo;

    public MyProgram(ResolveInfo resolveInfo,Boolean isLock){
        this.isLock = isLock;
        this.resolveInfo = resolveInfo;
    }

    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    public ResolveInfo getResolveInfo() {
        return resolveInfo;
    }

    public void setResolveInfo(ResolveInfo resolveInfo) {
        this.resolveInfo = resolveInfo;
    }
}
