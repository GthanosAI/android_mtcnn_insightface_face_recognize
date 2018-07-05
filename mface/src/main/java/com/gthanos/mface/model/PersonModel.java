package com.gthanos.mface.model;

import com.gthanos.mface.FaceFileConfig;

public class PersonModel extends JNIModel {
    private String name;
    private String sourceName;
    private Long id;

    public PersonModel(long id) {
        this.id = id;
        this.sourceName = FaceFileConfig.FACE_PERSON_DIR + String.valueOf(id);
    }

    public void modifyName(String name) {
        this.name = name;
        nativeModifyName(mNativePtr, name);
    }

    public PersonModel setSourceName(String sourceName) {
        this.sourceName = getFileName(id, sourceName);
        nativeModifySourceName(mNativePtr, this.sourceName);
        return this;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void release() {
        nativeRelease(mNativePtr);
    }

    public boolean dump() {
        return nativeDump(mNativePtr);
    }

    public static PersonModel createPerson(long id) {
        return nativeCreatePerson(id);
    }

    private static String getFileName(long id, String dir) {
        return dir + String.valueOf(id) + ".bin";
    }

    private native boolean nativeDump(long nativePtr);

    private static native void nativeRelease(long nativePtr);

    private native void nativeModifyName(long nativePtr, String name);

    private native void nativeModifySourceName(long nativePtr, String name);

    private static native PersonModel nativeCreatePerson(long id);

    private static native PersonModel nativeLoadPerson(String sourceName);

    static {
        System.loadLibrary("model");
    }
}
