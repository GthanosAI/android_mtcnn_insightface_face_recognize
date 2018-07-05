package cn.com.earth.net.subscriber;

import cn.com.earth.net.exception.ApiException;
import cn.com.earth.net.exception.ExceptionFactory;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午2:29
 */

public abstract class DefaultSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onComplete() {
        
    }

    @Override
    public void onError(Throwable throwable) {
        onError(ExceptionFactory.createException(throwable));
    }

    @Override
    public void onNext(T t) {

    }

    public abstract void onError(ApiException e);

}
