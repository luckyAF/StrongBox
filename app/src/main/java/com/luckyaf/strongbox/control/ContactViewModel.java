package com.luckyaf.strongbox.control;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import me.luckyaf.greendao.MyContact;

/**
 * 类描述：控制通讯录的显示
 *
 * @auhter Created by luckyAF on 16/4/27
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
