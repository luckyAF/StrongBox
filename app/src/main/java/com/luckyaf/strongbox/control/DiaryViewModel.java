package com.luckyaf.strongbox.control;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.luckyaf.strongbox.activity.EditDiaryActivity;
import com.luckyaf.strongbox.util.DateUtils;

import me.luckyaf.greendao.Diary;

/**
 * 类描述：控制日记显示
 *
 * @auhter Created by luckyAF on 16/4/10
 */
public class DiaryViewModel  extends BaseObservable {
    private Context mContext;
    private String id;
    private String title;        //标题
    private String content;
    private String editTime;     // 修改时间
    private String createTime;   // 创建时间

    public DiaryViewModel(Context context, Diary diary){
        this.mContext = context;
        setId(diary.getId().toString());
        setTitle(diary.getTitle());
        setContent(diary.getContent());
        setCreateTime(diary.getCreateTime());
        setEditTime(diary.getUpdateTime());
    }

    public void onItemClick(View view){
        Intent intent = new Intent(mContext, EditDiaryActivity.class);
        intent.putExtra(EditDiaryActivity.intent_is_update,true);
        intent.putExtra(EditDiaryActivity.intent_diary_id,getId());
        mContext.startActivity(intent);
    }

    public String showTime(){
        String result = "最近更新：" + DateUtils.TimeMills2Date(getEditTime());
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



}
