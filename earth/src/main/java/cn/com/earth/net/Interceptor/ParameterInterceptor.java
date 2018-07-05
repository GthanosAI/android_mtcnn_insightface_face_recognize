package cn.com.earth.net.Interceptor;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/3 下午8:00
 */

public class ParameterInterceptor implements Interceptor, INet {
    final static String JSON_BEAN_BODY_KEY = "json_body_key";
    protected Map<String, String> mapAppendParams = null;
    protected Map<String, Object> mapHeader = null;
    protected Map<String, Object> mapBody = null;
    private List<File> fileList = null;
    protected static Gson gson = new Gson();

    private String tag;

    public ParameterInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        return chain.proceed(precessBuild(oldRequest));
    }


    private Request precessBuild(Request oldRequest) {
        //apped url
        HttpUrl.Builder urlBuilder = oldRequest.url().newBuilder();
        if (mapAppendParams != null && !mapAppendParams.isEmpty()) {
            for (String key : mapAppendParams.keySet()) {
                if (key != null) {
                    urlBuilder.addQueryParameter(key, mapAppendParams.get(key));
                }
            }
        }

        Request.Builder newBuilder = oldRequest.newBuilder().url(urlBuilder.build());

        // header
        Map<String, Object> header = mapHeader;
        if (header != null && !header.isEmpty()) {
            for (String key : header.keySet()) {
                if (key != null) {
                    newBuilder.addHeader(key, String.valueOf(header.get(key)));
                }
            }
        }
        if (tag != null) {
            newBuilder.tag(tag);
        }


        return newBuilder.build();
    }

    private RequestBody getReqBody(String method) {
//        if (POST.equals(method) || PUT.equals(method) || DELETE.equals(method) || GET.equals(method)) {
//        } else {
//            return null;
//        }
//
//        Map<String, Object> objectMap = mapBody;
//
//        if (objectMap == null || objectMap.isEmpty()) {
//            return null;
//        }
//
//        if (isContainJsonObj()) {
//            return RequestBody.create(mediaType, gson.toJson(mapBody.get(JSON_BEAN_BODY_KEY)));
//        } else {
//            return RequestBody.create(mediaType, gson.toJson(objectMap));
//        }
        return null;
    }

    private <T extends Map> T checkNull(T t) {
        if (t == null) {
            t = (T) new HashMap<>();
        }

        return t;
    }

    private boolean isContainJsonObj() {
        return mapBody != null && mapBody.containsKey(JSON_BEAN_BODY_KEY);
    }

    public ParameterInterceptor putHeader(String key, String value) {
        mapHeader = checkNull(mapHeader);
        mapHeader.put(key, value);
        return this;
    }

    public ParameterInterceptor putBody(String key, Object value) {
        if (value != null) {
            mapBody = checkNull(mapBody);
            mapBody.put(key, value);
        }
        return this;
    }

    public ParameterInterceptor putBodyList(List<?> list) {
        mapBody = checkNull(mapBody);

        if (list != null) {
            mapBody.put(JSON_BEAN_BODY_KEY, list);
        }

        return this;
    }

    public ParameterInterceptor appendUrl(String key, String value) {
        mapAppendParams = checkNull(mapAppendParams);
        if (value != null) {
            mapAppendParams.put(key, value);
        }

        return this;
    }

    public ParameterInterceptor putFileList(List<File> files) {
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        fileList.addAll(files);
        return this;
    }
}
