package cn.com.earth.mvp;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: none
 * 时间:  2018/4/3 上午10:57
 */

public interface ILifecycle<V extends IEarthBaseView> {
    void attachView(V v);

    void detachView(V v);

    void destroy();

    V get();

    boolean isViewDestroy();

    boolean isAvailable();
}
