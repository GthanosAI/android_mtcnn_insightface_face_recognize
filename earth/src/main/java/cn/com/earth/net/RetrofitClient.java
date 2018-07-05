package cn.com.earth.net;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cn.com.earth.net.Interceptor.HttpLoggingInterceptor;
import cn.com.earth.net.proxy.ITokenExceptionManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/30 上午10:54
 */

public class RetrofitClient {

    private static OkHttpClient sOkHttpClient;
    private ITokenExceptionManager handler;
    private Retrofit retrofit = null;

    public void setHandler(ITokenExceptionManager handler) {
        this.handler = handler;
    }

    public static RetrofitClient getInstance() {
        return ClientHolder.instance;
    }

    private RetrofitClient() {

    }

    private static class ClientHolder {
        static RetrofitClient instance = new RetrofitClient();
    }


    public Retrofit getRetrofit(String url, Interceptor... interceptors) {
        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (interceptors != null) {
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }

            builder.addInterceptor(new HttpLoggingInterceptor());
            sOkHttpClient = builder.build();

            retrofit = new Retrofit.Builder().client(sOkHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        }

        return retrofit;
    }

    public Retrofit getRetrofit(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().client(
                    sOkHttpClient = new OkHttpClient().newBuilder()
                            .addInterceptor(new HttpLoggingInterceptor()).build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build();
        }

        return retrofit;
    }


    /**
     * 取消所有的网络请求
     */
    public void cancelAll() {
        if (sOkHttpClient == null) {
            sOkHttpClient.dispatcher().cancelAll();
        }
    }


}
