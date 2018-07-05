package cn.com.earth.net.exception;

import java.io.IOException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午8:39
 */

public class ApiTokenException extends IOException implements INetErrorCode {
    int errorCode;

    public ApiTokenException(int errorCode) {
        super(getErrorMessage(errorCode));
        this.errorCode = errorCode;
    }

    private static String getErrorMessage(int errorCode){
        if (errorCode == TOKEN_EXPIRED){
            return "token 过期";
        }else if (errorCode == TOKEN_NOT_FOUND){
            return "token 未设置";
        }

        return "未知错误";
    }

    public boolean isTokenExpired(){
        return errorCode == TOKEN_EXPIRED;
    }
}
