package com.jacky.facedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.stetho.common.LogUtil;
import com.jacky.facedemo.base.CommonActivity;

import cn.com.earth.base.IKey;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午8:51
 */

public class Jump implements IKey {

    enum TransitionType {
        RL,
        LR,
        DU,
        UD
    }

    static void toCommonActivityForResult(Activity context, String fragmentName, Bundle bundle, TransitionType type, int reqCode) {
        if (bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString(KEY_FRAGMENT_NAME, fragmentName);

        LogUtil.e("fragmentName;" + fragmentName);

        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, reqCode);

        overPendingTransition(context, type);
    }


    static void toCommonActivity(Context context, String fragmentName, Bundle bundle) {
        toCommonActivity(context, fragmentName, bundle, TransitionType.RL);
    }


    static void toCommonActivity(Context context, String fragmentName, Bundle bundle, TransitionType type) {
        if (bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString(KEY_FRAGMENT_NAME, fragmentName);

        LogUtil.e("fragmentName" + fragmentName);

        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);

        overPendingTransition(context, type);
    }

    static void overPendingTransition(Context context) {
        overPendingTransition(context, TransitionType.RL);
    }

    static void overPendingTransition(Context context, TransitionType type) {
        if (context != null && context instanceof Activity) {
            Activity activity = (Activity) context;
            if (type == TransitionType.RL) {
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            } else if (type == TransitionType.UD) {
                activity.overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            } else {
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        }
    }


    static void needFinish(Context context) {
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
