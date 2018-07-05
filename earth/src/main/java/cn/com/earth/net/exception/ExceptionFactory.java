package cn.com.earth.net.exception;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.UnknownHostException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/30 下午4:24
 */

public class ExceptionFactory implements INetErrorCode {
    public static ApiException createException(Throwable e) {
        e.printStackTrace();
        if (e instanceof JsonParseException) {
            return new ApiException(JSON_PARSE_ERROR, "数据解析错误");
        } else if (e instanceof HttpTimeException) {
            HttpTimeException exception = (HttpTimeException) e;
            ApiException apiException = new ApiException();
            apiException.setErrorCode(exception.getCode());
            apiException.setErrorMessage(exception.getMessage());
            return apiException;
        }else if (e instanceof UnknownHostException){
            return new ApiException(NO_NET, "网络异常请确认网络是否连接");
        }
        else if (e instanceof HttpException){
            ApiException apiException = new ApiException();
            apiException.setErrorCode(((HttpException) e).code());
            String msg = ((HttpException) e).message();
            if (TextUtils.isEmpty(msg)){
                msg = e.getMessage();
            }
            apiException.setErrorMessage(msg);
            return apiException;
        }else {
            return new ApiException(UNKOWN_ERROR, "系统错误");
        }
    }
}
