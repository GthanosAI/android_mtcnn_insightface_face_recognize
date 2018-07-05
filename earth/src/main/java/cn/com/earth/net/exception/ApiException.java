package cn.com.earth.net.exception;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/30 下午4:21
 */

public class ApiException extends Exception{
    protected int errorCode;
    protected String errorMessage;

    public ApiException(String message, String errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public ApiException(){}

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ApiException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
