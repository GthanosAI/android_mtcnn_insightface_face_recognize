package com.jacky.facedemo.face.checkface;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.jacky.facedemo.BR;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/4 上午1:20
 */

public class CheckFaceVm extends BaseObservable {
    @Bindable
    public String msg;

    @Bindable
    public boolean login;

    @Bindable
    public String info;

    public String getInfo() {
        return info;
    }

    public CheckFaceVm setInfo(String info) {
        this.info = info;
        notifyPropertyChanged(BR.info);
        return this;
    }

    public boolean isLogin() {
        return login;
    }

    public CheckFaceVm setLogin(boolean login) {
        this.login = login;
        notifyPropertyChanged(BR.login);
        return this;
    }

    public String getMsg() {
        return msg;
    }


    public CheckFaceVm setMsg(String msg) {
        this.msg = msg;
        notifyPropertyChanged(BR.msg);
        return this;
    }
}
