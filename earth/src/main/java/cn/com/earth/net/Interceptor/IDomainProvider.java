package cn.com.earth.net.Interceptor;

import okhttp3.HttpUrl;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: none
 * 时间:  2018/3/1 下午2:56
 */

public interface IDomainProvider {
    HttpUrl getDomain(String identification);

    String getMainUrl();
}
