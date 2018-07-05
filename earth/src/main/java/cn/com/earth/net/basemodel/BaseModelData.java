package cn.com.earth.net.basemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.com.earth.net.exception.ApiException;

/**
 * 介绍: 通常的数据结构
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/1/3 下午11:25
 */

public class BaseModelData<T> {

    public String message;//错误的时候肯定有
    @SerializedName("code")
    public int flag;//错误码
    public int err;
    public T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return getFlag() == 0;
    }

    public boolean isEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    public ApiException getApiException(){
        return new ApiException(flag, message);
    }

}
