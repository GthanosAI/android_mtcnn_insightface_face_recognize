package cn.com.earth.net.exception;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午2:13
 */

public interface INetErrorCode {
    int UNKOWN_ERROR = 0x1002;
    int NO_CHACHE_ERROR = 0x1003;
    int CHACHE_TIME_OUT_ERROR = 0x1004;
    int NO_NET = 0x10008;
    int JSON_PARSE_ERROR = 0x2005;

    int TOKEN_EXPIRED = 0x30001;
    int TOKEN_NOT_FOUND = 0x3002;
}
