package com.jacky.facedemo;

import android.content.Context;
import android.os.Bundle;

import com.jacky.facedemo.face.EditPersonFragment;
import com.jacky.facedemo.face.addface.AddFaceFragment;
import com.jacky.facedemo.face.checkface.CheckFaceFragment;
import com.jacky.facedemo.face.facecompare.CompareFaceFragment;


public class Router extends Jump {
    public static void toHome(Context context) {
        toCommonActivity(context, HomeFragment.class.getName(), null);
        needFinish(context);
    }

    public static void toFaceAdd(Context context, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STRING, name);
        toCommonActivity(context, AddFaceFragment.class.getName(), bundle);
    }

    public static void toFaceVerify(Context context) {
        toCommonActivity(context, CheckFaceFragment.class.getName(), null);
    }

    public static void toCompare(Context context) {
        toCommonActivity(context, CompareFaceFragment.class.getName(), null);
    }

    public static void toFaceInput(Context context) {
        toCommonActivity(context, EditPersonFragment.class.getName(), null);
    }

    public static void toDemo(Context context) {
        toCommonActivity(context, DemoFragment.class.getName(), null);
    }
}
