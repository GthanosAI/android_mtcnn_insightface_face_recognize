package cn.com.earth.net.Interceptor;

import android.support.annotation.IntDef;
import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/12/22 下午9:59
 */

public class HttpLoggingInterceptor implements Interceptor {

    static final String Tag = "smart_pos_HTTP_LOG";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Level
    static final int NONE = 1;
    @Level
    static final int BASIC = 2;
    @Level
    static final int HEADERS = 3;
    @Level
    static final int BODY = 4;
    private volatile int printLevel = BODY;

    /**
     * NONE:      不打印log
     * BASIC:     只打印 请求首行 和 响应首行
     * HEADERS:   打印请求和响应的所有 Header
     * BODY:      所有数据全部打印
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NONE, BASIC, HEADERS, BODY})
    public @interface Level {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (printLevel == NONE) {
            return chain.proceed(request);
        }

        logingForRequest(request);

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            return logForResponse(response, tookMs);
        } catch (Exception e) {
            log("http failed:" + e);
            throw e;
        }

    }

    private Response logForResponse(Response response, long tookMs) {
        Response clone = response.newBuilder().build();
        ResponseBody responseBody = clone.body();
        boolean logBody = (printLevel == BODY);
        boolean logHeader = (printLevel == BODY || printLevel == HEADERS);

        try {
            log("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeader) {
                Headers headers = clone.headers();
                StringBuilder builder = null;
                if (headers.size() > 0) {
                    builder = new StringBuilder();
                }

                for (int i = 0, count = headers.size(); i < count; i++) {
                    builder.append(headers.name(i) + ":" + headers.value(i) + ",");
                }

                log(builder.toString());

                if (logBody) {
                    if (isPlaintext(responseBody.contentType())) {
                        String body = responseBody.string();
                        log("\tbody:" + body);
                        responseBody = ResponseBody.create(responseBody.contentType(), body);
                        return response.newBuilder().body(responseBody).build();
                    } else {
                        log("\tbody: maybe [file part] , too large too print , ignored!");
                    }
                }

            }
        } catch (Exception e) {

        }

        return response;
    }

    protected void logingForRequest(Request request) {
        boolean logBody = (printLevel == BODY);
        boolean logHeaders = (printLevel == BODY || printLevel == HEADERS);
        RequestBody requestBody = request.body();
        boolean hasBody = requestBody != null;


        StringBuilder builder = new StringBuilder();
        builder.append("methord: " + request.method());
        builder.append(",url: " + request.url());

        if (logHeaders) {
            Headers headers = request.headers();
            for (int i = 0; i < headers.size(); i++) {
                builder.append(headers.name(i) + ":" + headers.value(i) + ",");
            }

            log(builder.toString());

            if (logBody && hasBody) {
                if (isPlaintext(requestBody.contentType())) {
                    bodyToString(request);
                } else {
                    log("\tbody: maybe [file part] , too large too print , ignored!");
                }
            }
        }


    }

    protected void bodyToString(Request request) {
        Request copy = request.newBuilder().build();
        Buffer buffer = new Buffer();
        try {
            copy.body().writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = copy.body().contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            log("\tbody:" + buffer.readString(charset));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") ||
                    subtype.contains("json") ||
                    subtype.contains("xml") ||
                    subtype.contains("html")) //
                return true;
        }
        return false;
    }

    public void log(String message) {
        Log.e(Tag, message);
    }

}
