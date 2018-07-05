package cn.com.earth.net.convertfactory;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import cn.com.earth.net.basemodel.BaseModelData;
import cn.com.earth.net.exception.ApiTokenException;
import cn.com.earth.net.exception.INetErrorCode;
import cn.com.earth.net.exception.SeverException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/31 下午9:08
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            BaseModelData apiModel = (BaseModelData) adapter.fromJson(value.charStream());
            if (apiModel.err == INetErrorCode.TOKEN_NOT_FOUND) {
                throw new ApiTokenException(INetErrorCode.TOKEN_NOT_FOUND);
            } else if (apiModel.err == INetErrorCode.TOKEN_EXPIRED) {
                throw new ApiTokenException(INetErrorCode.TOKEN_EXPIRED);
            } else if (!apiModel.isSuccess()) {
                // 特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理
                throw new SeverException(apiModel.err, apiModel.message);
            } else if (apiModel.isSuccess()) {
                return apiModel.data;
            }
        } finally {
            value.close();
        }
        return null;
    }
}
