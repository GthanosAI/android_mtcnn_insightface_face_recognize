package cn.com.earth.net.Interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: none
 * 时间:  2018/3/1 上午11:03
 */

public class MultiDomainInterceptor implements Interceptor, ISupportMultiDomain {
    private UrlParser urlParser;
    private IDomainProvider provider;
    private boolean needSuportMultiDomains;

    public MultiDomainInterceptor(IDomainProvider provider, boolean needSupportMultiDomains) {
        this.urlParser = new UrlParser();
        this.provider = provider;
        this.needSuportMultiDomains = needSupportMultiDomains;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (needSuportMultiDomains) {
            return chain.proceed(processRequest(chain.request()));
        } else {
            return chain.proceed(chain.request());
        }
    }

    private Request processRequest(Request request) {
        Request.Builder builder = request.newBuilder();

        String url = request.url().toString();
        if (url.contains(IDENTIFICATION_IGNORE)) {
            return processIdentifyIgnoreProcess(builder, url);
        }

        String domainName = obtainRightDomain(request);

        HttpUrl httpUrl = null;
        if (!TextUtils.isEmpty(domainName)) {
            httpUrl = provider.getDomain(domainName);
            builder.removeHeader(DOMAIN_NAME);
        }

        if (httpUrl != null) {
            HttpUrl newUrl = urlParser.parserUrl(httpUrl, request.url());
//            LogUtils.e("change url:%s ---> %s---", newUrl.url().toString(), request.url().toString());
            return builder.url(newUrl).build();
        }

        return builder.build();
    }

    private String obtainRightDomain(Request request) {
        List<String> headers = request.headers(DOMAIN_NAME);
        if (headers == null || headers.size() == 0) {
            return null;
        } else if (headers.size() > 1) {
            throw new IllegalArgumentException("domain 使用错误");
        } else {
            return request.header(DOMAIN_NAME);
        }
    }

    private Request processIdentifyIgnoreProcess(Request.Builder newBuilder, String url) {
        String[] splits = url.split(IDENTIFICATION_IGNORE);
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : splits) {
            stringBuffer.append(s);
        }

        return newBuilder.url(stringBuffer.toString()).build();
    }


    private class UrlParser {
        public HttpUrl parserUrl(HttpUrl domainUrl, HttpUrl url) {
            if (domainUrl == null) return null;

            return url.newBuilder()
                    .scheme(domainUrl.scheme())
                    .host(domainUrl.host())
                    .port(domainUrl.port())
                    .build();
        }
    }
}
