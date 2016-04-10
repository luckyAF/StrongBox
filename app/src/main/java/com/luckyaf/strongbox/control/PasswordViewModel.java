package com.luckyaf.strongbox.control;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.luckyaf.strongbox.BR;
import com.luckyaf.strongbox.activity.EditPassWordActivity;
import com.luckyaf.strongbox.util.DateUtils;

import me.luckyaf.greendao.CodeBook;

/**
 * 类描述：控制密码显示
 *
 * @auhter Created by luckyAF on 16/3/31
 */
public class PasswordViewModel extends BaseObservable {
    private String id;



    private String title;        //标题                    // 100
    private String userName;     //用户名                  // 100
    private String passWord;     // 密码                  //  100
    private String describe;     //描述                   //  200
    private String editTime;     // 修改时间
    private String createTime;   // 创建时间              key
    private Boolean isCanSee;    //密码 是否可见
    private Boolean isUpdate;    // 是否为更新新建密码



    private Context mContext;

    public PasswordViewModel(Context context){
        mContext = context;
        initData();
    }

    public PasswordViewModel(Context context,CodeBook codeBook){
        mContext = context;
        this.setId(codeBook.getId().toString());
        this.setPassWord(codeBook.getPassword());
        this.setCreateTime(codeBook.getCreateTime());
        this.setCanSee(codeBook.getCanSee());
        this.setTitle(codeBook.getTitle());
        this.setUserName(codeBook.getUsername());
        this.setDescribe(codeBook.getDescribe());
        this.setEditTime(codeBook.getUpdateTime());
    }

    private void initData(){
        id = null;
        title = null;
        userName = null;
        passWord = null;
        describe = null;
        editTime = null;
        isCanSee = false;
        isUpdate = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getUpdate() {
        return isUpdate;
    }

    public void setUpdate(Boolean update) {
        isUpdate = update;
    }

    @Bindable
    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Bindable
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Bindable
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Bindable
    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    @Bindable
    public Boolean getCanSee() {
        return isCanSee;
    }

    public void setCanSee(Boolean canSee) {
        isCanSee = canSee;
    }



    public void onClickCanSee(View view){
        isCanSee = !isCanSee;
        notifyPropertyChanged(BR.canSee);
        notifyPropertyChanged(BR.passWord);
    }

    public void onCliickDelete(View view){

    }

    public CodeBook Model2Dao(){
        CodeBook codeBook = new CodeBook();
        codeBook.setCreateTime(DateUtils.currentTime2String());
        codeBook.setCanSee(getCanSee());
        codeBook.setTitle(getTitle());
        codeBook.setUsername(getUserName());
        codeBook.setPassword(getPassWord());
        codeBook.setDescribe(getDescribe());
        return codeBook;
    }

    public void onItemClick(View view){
        Intent intent = new Intent(mContext, EditPassWordActivity.class);
        intent.putExtra(EditPassWordActivity.intent_is_update,true);
        intent.putExtra(EditPassWordActivity.intent_codeBook_id,getId());
        mContext.startActivity(intent);
    }
}
