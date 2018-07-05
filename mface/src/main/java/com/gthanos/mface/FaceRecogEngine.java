package com.gthanos.mface;


import com.gthanos.mface.model.SearchResult;

public class FaceRecogEngine {

    public native boolean init(String path);

    public native double compare(Object o1, Object o2, int w1, int h1, int w2, int h2);

    public native boolean search(Object o, String datPath, int w, int h, SearchResult searchResult);

    public native boolean addFace(Object o, long ptr, int w, int h);

    public native static void clear();

    public native boolean addFaceYuv(byte[] yuvData, long ptr, int w, int h, int degree, boolean isMirror);

    public native boolean searchFaceYuv(byte[] yuvData, int w, int h, int degree, boolean isMirror, String dataPath,
                                        SearchResult searchResult);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                clear();
            }
        });

        System.loadLibrary("faceEngine");
    }
}
