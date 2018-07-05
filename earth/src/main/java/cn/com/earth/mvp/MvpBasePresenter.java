package cn.com.earth.mvp;

import java.lang.ref.WeakReference;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: none
 * 时间:  2018/4/3 上午10:56
 */

public class MvpBasePresenter<V extends IEarthBaseView> implements IEarthBasePresenter, ILifecycle<V> {
    private WeakReference<V> viewRef;

    @Override
    public void start() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void attachView(V v) {
        viewRef = new WeakReference<>(v);
    }

    @Override
    public void detachView(V v) {

    }

    @Override
    public void destroy() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public V get() {
        if (viewRef != null) {
            return viewRef.get();
        }
        return null;
    }

    @Override
    public boolean isViewDestroy() {
        return get() == null;
    }

    @Override
    public boolean isAvailable() {
        return get() != null;
    }
}
