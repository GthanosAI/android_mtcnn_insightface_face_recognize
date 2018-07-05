package cn.com.earth.net.proxy;

import android.text.TextUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import cn.com.earth.net.exception.ApiTokenException;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.http.Query;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午9:24
 */

public class TokenProxyHandler implements InvocationHandler {
    private final static String TOKEN = "tk";
    ITokenExceptionManager tokenExceptionManager;
    private Object mProxyObject;
    private boolean mIsTokenNeedRefresh;
    private Throwable mRefreshTokenError = null;

    public TokenProxyHandler(Object o, ITokenExceptionManager tokenExceptionManager) {
        this.tokenExceptionManager = tokenExceptionManager;
        mProxyObject = o;
        this.mIsTokenNeedRefresh = false;
    }

    @Override
    public Object invoke(Object o, final Method method, final Object[] objects) throws Throwable {
        return Flowable.just(null).flatMap(new Function<Object, Flowable<?>>() {
            @Override
            public Flowable<?> apply(Object o) throws Exception {
                if (mIsTokenNeedRefresh) {
                    updateMethodToken(method, objects);
                }
                return (Flowable<?>) method.invoke(mProxyObject, objects);
            }
        }).retryWhen(new Function<Flowable<? extends Throwable>, Flowable<?>>() {
            @Override
            public Flowable<?> apply(Flowable<? extends Throwable> flowable) throws Exception {
                return flowable.flatMap(new Function<Throwable, Flowable<?>>() {
                    @Override
                    public Flowable<?> apply(Throwable throwable) throws Exception {
                        if (tokenExceptionManager != null) {
                            if (throwable instanceof ApiTokenException)
                                if (((ApiTokenException) throwable).isTokenExpired()) {
                                    (tokenExceptionManager.refreshToken()).subscribe(new Subscriber<TokenModel>() {
                                        @Override
                                        public void onError(Throwable throwable) {
                                            mRefreshTokenError = throwable;
                                        }

                                        @Override
                                        public void onComplete() {

                                        }

                                        @Override
                                        public void onSubscribe(Subscription s) {

                                        }

                                        @Override
                                        public void onNext(TokenModel tokenModel) {
                                            if (tokenModel != null) {
                                                mIsTokenNeedRefresh = true;
                                                tokenExceptionManager.setToken(tokenModel);
                                            }
                                        }
                                    });


                                    if (mRefreshTokenError != null) {
                                        return Flowable.error(mRefreshTokenError);
                                    } else {
                                        return Flowable.just(true);
                                    }


                                } else {
                                    tokenExceptionManager.reLogin();
                                }
                        }
                        return Flowable.error(throwable);
                    }
                });
            }


        });
    }

    private void updateMethodToken(Method method, Object[] args) {
        if (mIsTokenNeedRefresh && !TextUtils.isEmpty(tokenExceptionManager.getToken())) {
            Annotation[][] annotationsArray = method.getParameterAnnotations();
            Annotation[] annotations;
            if (annotationsArray != null && annotationsArray.length > 0) {
                for (int i = 0; i < annotationsArray.length; i++) {
                    annotations = annotationsArray[i];
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Query) {
                            if (TOKEN.equals(((Query) annotation).value())) {
                                args[i] = tokenExceptionManager.getToken();
                            }
                        }
                    }
                }
            }
            mIsTokenNeedRefresh = false;
        }
    }
}
