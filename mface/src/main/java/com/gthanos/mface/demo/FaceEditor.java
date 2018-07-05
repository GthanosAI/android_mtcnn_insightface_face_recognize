package com.gthanos.mface.demo;

import android.graphics.Bitmap;

import com.gthanos.mface.FaceEngineManager;
import com.gthanos.mface.model.PersonModel;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/2 下午4:39
 */

public class FaceEditor {
    static class InstanceHolder {
        static FaceEditor instance = new FaceEditor();
    }

    public static FaceEditor getInstance() {
        return InstanceHolder.instance;
    }

    private PersonModel personModel;

    public PersonModel newPersonModel() {
        long id = System.currentTimeMillis();
        personModel = PersonModel.createPerson(id);
        return personModel;
    }

    public PersonModel getPersonModel() {
        return personModel;
    }

    public void clear() {
        if (personModel != null) {
            personModel.release();
        }
    }

    public void modifyName(String name) {
        if (personModel != null) {
            personModel.modifyName(name);
        }
    }

    public void addFace(Bitmap bitmap) {
        boolean result = FaceEngineManager.getInstance().addFace(bitmap, personModel.getNativePtr());
    }

    public void dump() {
        if (personModel != null) {
            personModel.dump();
        }
    }
}
