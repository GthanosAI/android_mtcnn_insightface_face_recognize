package cn.com.earth.net.exception;

import java.io.IOException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午9:17
 */

public class SeverException extends IOException{
    private int code;
    private String msg;

    public SeverException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
