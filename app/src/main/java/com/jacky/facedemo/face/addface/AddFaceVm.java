package com.jacky.facedemo.face.addface;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.jacky.facedemo.BR;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午8:15
 */

public class AddFaceVm extends BaseObservable {

    @Bindable
    private boolean isLogin;
    @Bindable
    private String showMsg;

    public boolean isLogin() {
        return isLogin;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public AddFaceVm setLogin(boolean login) {
        isLogin = login;
        notifyPropertyChanged(BR.isLogin);
        return this;
    }

    public AddFaceVm setShowMsg(String showMsg) {
        notifyPropertyChanged(BR.showMsg);
        this.showMsg = showMsg;
        return this;
    }

    public AddFaceVm init(){
        setLogin(true);
        setShowMsg("正在录入...");
        return this;
    }
}
