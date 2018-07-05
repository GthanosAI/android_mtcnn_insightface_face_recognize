package cn.com.earth.net.exception;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 上午10:22
 */

public class HttpTimeException extends RuntimeException implements INetErrorCode {

    private int code;

    public HttpTimeException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public HttpTimeException(int code){
        this(getErrorMessage(code));
        this.code = code;
    }

    private static String getErrorMessage(int code) {
        switch (code){
            case UNKOWN_ERROR:
                return "网络错误";
            case NO_CHACHE_ERROR:
                return "无缓存数据";

            case CHACHE_TIME_OUT_ERROR:
                return "缓存数据过期";

            default:
                return "未知错误";
        }
    }
}
