package cn.com.earth.net.proxy;

import io.reactivex.Flowable;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午9:25
 */

public interface ITokenExceptionManager {

    Flowable<TokenModel> refreshToken();

    void reLogin();


    String getToken();

    void setToken(TokenModel tokenModel);
}
