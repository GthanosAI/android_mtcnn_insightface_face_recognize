package com.gthanos.mface;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.gthanos.mface.model.SearchResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/6/25 下午5:16
 */

public class FaceEngineManager {
    private static String TAG = "FaceEngineManager";
    private FaceRecogEngine faceRecogEngine;
    private final static String MODEL_PATH = FaceFileConfig.FACE_LIB_DIR + "models" + File.separator;
    private final static String DETECT_MODEL_PATH = FaceFileConfig.FACE_LIB_DIR + "models" + File.separator;

    public void init(Context context) {
        try {
            checkAndMakeDir(FaceFileConfig.FACE_LIB_DIR);
            checkAndMakeDir(FaceFileConfig.FACE_PERSON_DIR);
            checkAndMakeDir(FaceFileConfig.FACE_IMG_DIR);
            copyBigDataToSD(context, "model.dat", MODEL_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        faceRecogEngine.init(MODEL_PATH);
    }

    private FaceEngineManager() {
        faceRecogEngine = new FaceRecogEngine();
    }

    public static FaceEngineManager getInstance() {
        return Holder.instance;
    }

    static class Holder {
        static FaceEngineManager instance = new FaceEngineManager();
    }

    private void checkAndMakeDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

    }


    private void copyBigDataToSD(Context context, String strOutFileName, String destPath) throws IOException {
        checkAndMakeDir(destPath);

        String tmpFile = destPath + strOutFileName;
        File f = new File(tmpFile);
        if (f.exists()) {
            return;
        }
        InputStream myInput;
        java.io.OutputStream myOutput = new FileOutputStream(destPath + strOutFileName);
        myInput = context.getAssets().open(strOutFileName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    private void toWriteToSingleFile(Context context, String fileName, String[] fileArray) {

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        InputStream myInput;
        try {
            java.io.OutputStream myOutput = new FileOutputStream(fileName, true);

            long total = 0;
            for (String tmpFile : fileArray) {
                total = 0;
                myInput = context.getAssets().open(tmpFile);
                byte[] buffer = new byte[1024];
                int length = myInput.read(buffer);

                total = length;

                while (length > 0) {
                    myOutput.write(buffer, 0, length);
                    length = myInput.read(buffer);
                    total += length;
                }
                myInput.close();
            }
            myOutput.flush();
            myOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized float compare(Bitmap b1, Bitmap b2) {
        long t1 = System.currentTimeMillis();
        float diff = (float) faceRecogEngine.compare(b1, b2, b1.getWidth(), b1.getHeight(), b2.getWidth(), b2.getHeight());
        Log.e("[time--debug]", System.currentTimeMillis() - t1 + "");
        return diff;
    }

    public synchronized boolean addFace(Bitmap bitmap, long ptr) {
        return faceRecogEngine.addFace(bitmap, ptr, bitmap.getWidth(), bitmap.getHeight());
    }

    public synchronized boolean search(Bitmap bitmap, String path, SearchResult result) {
        return faceRecogEngine.search(bitmap, path, bitmap.getWidth(), bitmap.getHeight(), result);
    }

    public synchronized boolean addFace(byte[] yuvData, long ptr, int w, int h, int d, boolean mirror, boolean isSave) {
        String savePath = isSave ? FaceFileConfig.FACE_IMG_DIR : "";

        return faceRecogEngine.addFaceYuv(yuvData, ptr, w, h, d, mirror, isSave, savePath);
    }

    public synchronized boolean search(byte[] yuvData, int w, int h, int d, boolean mirror, String path, SearchResult result) {
        return faceRecogEngine.searchFaceYuv(yuvData, w, h, d, mirror, path, result);
    }


}

