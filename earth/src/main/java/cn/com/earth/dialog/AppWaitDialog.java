package cn.com.earth.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.earth.R;
import cn.com.earth.widget.loadview.AppProgressBar;


/**
 * 介绍: ${描述}
 * 作者: jacky
 *
 * 时间:  17/1/3 下午4:21
 */
public class AppWaitDialog implements IWaitDialogView {

    private Activity activity;
    private Dialog dialog;
    private AppProgressBar progressBar;
    private ImageView image;
    private TextView titleText;
    private Handler handler;

    public AppWaitDialog(Activity activity) {
        this.activity = activity;
        handler = new Handler();
    }

    private void init() {
        if (null != activity) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            View view = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.earth_wait_dialog_layout, null);
            image = (ImageView) view.findViewById(R.id.cst_wait_dialog_img);
            progressBar = (AppProgressBar) view.findViewById(R.id.app_progressbar);
            titleText = (TextView) view.findViewById(R.id.cst_wait_dialog_text);
            progressBar.setProgressBarBackgroundColor(Color.parseColor("#80000000"));
            dialog.setContentView(view);
            progressBar.startAnimation();
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return true;
                }
            });
        }
    }

    /**
     * @param title
     * @param isProgress
     * @param imgRes     显示滚动条的时候该值传递null
     */
    public void show(String title, boolean isProgress, Integer imgRes) {
        if (null == dialog) {
            init();
        }
        if (null != activity && activity.isFinishing()) {
            return;
        }

        if (null != dialog) {
            if (!dialog.isShowing()) {
                dialog.cancel();
            }

            dialog.show();
        }
        setDialog(title, isProgress, imgRes);
    }

    private void setDialog(String title, boolean isProgress, Integer imgRes) {
        if (null != titleText && !TextUtils.isEmpty(title)) {
            titleText.setText(title);
        }
        if (null != progressBar && null != image) {
            if (isProgress) {
                progressBar.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                progressBar.startAnimation();
            } else {
                progressBar.stopAnimation();
                progressBar.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
            }
        }
        if (null != imgRes && null != image && null != activity) {
            image.setBackgroundDrawable(activity.getResources().getDrawable(imgRes));
        }
    }


    public void cancel() {
        if (null != dialog && null != activity && !activity.isFinishing()) {
            dialog.cancel();
            if (null != progressBar) {
                progressBar.stopAnimation();
            }
        }
    }

    public void delayCancel(int time) {
        if (null != handler) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            }, time);
        }
    }

    public void delayCancel(int time, final DelayCancelListener listener) {
        if (null != handler) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancel();
                    if (null != listener) {
                        listener.onDeal();
                    }
                }
            }, time);
        }
    }

    public boolean isShowing() {
        if (null != dialog) {
            return dialog.isShowing();
        }
        return false;
    }

    public void setCancelable(boolean cancelable) {
        if (null != dialog) {
            dialog.setCancelable(cancelable);
        }
    }

    @Override
    public void showWaitDialog(String title) {
        setCancelable(false);
        show(title, true, null);
    }

    @Override
    public void closeDialog() {
        if (isShowing()) {
            cancel();
        }
    }

    @Override
    public void delayCloseWithAction(String title, int delayMs, DelayCancelListener l) {
        show(title, true, null);
        delayCancel(delayMs, l);
    }

    @Override
    public void delayCloseWithAction(String title, int delayMs, DelayCancelListener l, boolean isCancelAble) {
        setCancelable(isCancelAble);
        show(title, true, null);
        delayCancel(delayMs, l);
    }

    @Override
    public void delayCloseWithActionCancelAble(String title, int delayMs, boolean isCancelAble) {
        setCancelable(isCancelAble);
        show(title, true, null);
        delayCancel(delayMs, null);
    }
    @Override
    public void delayCloseWithSuccess(String title) {
        delayCloseWithActionCancelAble(title, 800, false);
    }

    @Override
    public void delayCloseWithFail(String title) {
        delayCloseWithActionCancelAble(title, 3000, true);
    }

    @Override
    public Activity getHostActivity() {
        return activity;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    public interface DelayCancelListener {
        void onDeal();
    }
}
