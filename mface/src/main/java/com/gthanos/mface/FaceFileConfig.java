package com.gthanos.mface;

import android.os.Environment;

import java.io.File;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/2 下午4:46
 */

public interface FaceFileConfig {
    String FACE_LIB_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "face_lib" + File.separator;

    String FACE_PERSON_DIR = FACE_LIB_DIR + "data/";
    String FACE_IMG_DIR = FACE_LIB_DIR + "image_data/";
}

