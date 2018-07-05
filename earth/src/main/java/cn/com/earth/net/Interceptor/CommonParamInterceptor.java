package cn.com.earth.net.Interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/4/18 下午12:00
 */

public class CommonParamInterceptor implements Interceptor {

    Map<String, Object> param = new HashMap<>();

    public CommonParamInterceptor addParam(String key, Object value) {
        param.put(key, value);
        return this;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(processRequest(chain.request()));
    }

    private Request processRequest(Request request) {
        Request.Builder builder = request.newBuilder();
        for (String key : param.keySet()) {
            builder.addHeader(key, String.valueOf(param.get(key)));
        }
        return builder.build();
    }
}
