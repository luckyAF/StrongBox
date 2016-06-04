package com.luckyaf.strongbox.control;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.luckyaf.strongbox.activity.EditContactActivity;
import com.luckyaf.strongbox.activity.EditDiaryActivity;

import me.luckyaf.greendao.MyContact;

/**
 * 类描述：控制通讯录的显示
 *
 * @author Created by luckyAF on 16/4/27
 */
public class ContactViewModel extends BaseObservable {
    public Context mContext;
    public String id;
    public String name;
    public String phone;
    public String email;
    public String address;
    public String remark;

    public ContactViewModel(Context context, MyContact contact){
        this.mContext = context;
        setId(contact.getId().toString());
        setName(contact.getName());
        setPhone(contact.getPhone());
        setEmail(contact.getEmail());
        setAddress(contact.getAddress());
        setRemark(contact.getRemark());
    }

    public void onItemClick(View view){
        Intent intent = new Intent(mContext, EditContactActivity.class);
        intent.putExtra(EditContactActivity.intent_is_update,true);
        intent.putExtra(EditContactActivity.intent_contact_id,getId());
        mContext.startActivity(intent);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }




}
