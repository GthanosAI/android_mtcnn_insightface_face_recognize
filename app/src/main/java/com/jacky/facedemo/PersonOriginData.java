package com.jacky.facedemo;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午3:38
 */

public class PersonOriginData {
    private String name;
    private List<String> imgs;

    public String getName() {
        return name;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public PersonOriginData setName(String name) {
        this.name = name;
        return this;
    }

    public PersonOriginData addImage(String... datas) {
        if (imgs == null) {
            imgs = new ArrayList<>();
        }

        for (int i =0; i< datas.length; i++) {
            if (i == 2){
                continue;
            }
            imgs.add(DataSet.PATH + datas[i]);
        }

        return this;
    }

}
