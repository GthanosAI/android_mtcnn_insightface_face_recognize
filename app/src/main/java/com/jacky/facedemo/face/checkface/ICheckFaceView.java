package com.jacky.facedemo.face.checkface;

import com.gthanos.mface.model.SearchResult;

import cn.com.earth.mvp.IEarthBaseView;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/4 上午1:21
 */

public interface ICheckFaceView extends IEarthBaseView{
    void checkSuccess(SearchResult searchResult);
}
