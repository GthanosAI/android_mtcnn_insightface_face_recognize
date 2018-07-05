package com.jacky.facedemo;

import android.os.Environment;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/6/27 下午2:34
 */

public class DataSet {

    public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image_data/";

    public static DemoFragment.Param[] testImage = {
//            new DemoFragment.Param().setImg1(PATH + "test_t.jpg").setImg2(PATH + "h4.jpg"),
    };

    public static PersonOriginData[] personOriginData = {
//            new PersonOriginData().setName("022").addImage("022_0.jpeg", "022_1.jpeg", "022_2.jpeg", "022_3.jpeg", "022_4.jpeg"),
    };

    public static String[] targetImages = {
            PATH + "021_2.jpeg",
            PATH + "022_2.jpeg",
    };

    public static String targetImage = PATH + "021_1.jpeg";
}
