package cn.com.earth.tools.toast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.earth.R;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  16/12/21 下午11:54
 */

public class AppToast extends Toast {

    private View view;              // 自定义的view
    private RelativeLayout firstRow;// 第一排的布局
    private ImageView toastImg;     // 图片
    private TextView toastText;     // 图片右侧的文字
    private TextView toastTextBImg; // 图片下方的文字

    public final static int NO_IMG = -1;

    private final static int TYPE_TOW_ROW_IMG_STR_STR = 1; // 两排显示：第一排图片加文字，第二排文字
    private final static int TYPE_ONE_ROW_IMG_STR = 2;     // 一排显示图片和文字
    private final static int TYPE_ONE_ROW_STR = 3;         // 一排显文字
    private final static int TYPE_ONE_ROW_IMG = 4;         // 一排显示图片
    private final static int TYPE_TWO_ROW_IMG_STR = 5;     // 两排显示：第一排图片 ，第二排文字

    AppToast(Context context) {
        super(context);
        setGravity(Gravity.CENTER, 0, 0);
        setDuration(LENGTH_SHORT);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (null != inflater) {
            view = creatView(context);
            if (null != view) {
                setView(view);
            }
        }
    }

    private View creatView(Context context) {
        if (null != context) {
            RelativeLayout layout = new RelativeLayout(context);
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setGravity(Gravity.CENTER);
            int l = convertDIP2PX(context, 15);
            int t = convertDIP2PX(context, 10);
            layout.setPadding(l, t, l, t);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(10);
            drawable.setColor(Color.parseColor("#b0222222"));
            layout.setBackgroundDrawable(drawable);
            layout.setId(R.id.app_toast_layout);

            firstRow = new RelativeLayout(context);
            firstRow.setId(R.id.app_toast_first_row);

            //图片
            toastImg = new ImageView(context);
            toastImg.setId(R.id.app_toast_img);
            RelativeLayout.LayoutParams imgParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            imgParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            //提示语句
            toastText = new TextView(context);
            toastText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            toastText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            toastText.setTextColor(Color.WHITE);
            toastText.setId(R.id.app_toast_text);
            RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textParams.addRule(RelativeLayout.ALIGN_TOP, toastImg.getId());
            textParams.addRule(RelativeLayout.RIGHT_OF, toastImg.getId());
            firstRow.addView(toastImg, imgParams);
            firstRow.addView(toastText, textParams);

            layout.addView(firstRow);

            //图片下面的提示
            toastTextBImg = new TextView(context);
            toastTextBImg.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            toastTextBImg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            toastTextBImg.setTextColor(Color.WHITE);
            toastTextBImg.setId(R.id.app_toast_text_img);
            RelativeLayout.LayoutParams textBelowImgParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textBelowImgParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            textBelowImgParams.addRule(RelativeLayout.BELOW, firstRow.getId());
            textBelowImgParams.setMargins(0, 10, 0, 0);
            layout.addView(toastTextBImg, textBelowImgParams);
            int width = getWidth(context) * 2 / 3;
            if (0 == width) {
                width = 400;
            }
            toastText.setMaxWidth(width);
            toastTextBImg.setMaxWidth(width);
            return layout;
        }
        return null;
    }

    /**
     * 当前已经有Toast显示时，那么只对其中的内容进行替换
     *
     * @param type type
     * @param imgRes imgRes
     * @param toastTextStr teststr
     * @param toastBelowImgStr toastBelowImgStr
     */
    public void setContent(final int type,
                           final int imgRes,
                           final String toastTextStr,
                           final String toastBelowImgStr) {
        switch (type) {
            case TYPE_TOW_ROW_IMG_STR_STR:
                setHorizontalContent(imgRes, toastTextStr, toastBelowImgStr);
                break;
            case TYPE_ONE_ROW_IMG_STR:
                setHorizontalContent(imgRes, toastTextStr);
                break;
            case TYPE_ONE_ROW_STR:
                setHorizontalContent(toastTextStr);
                break;
            case TYPE_ONE_ROW_IMG:
                setHorizontalContent(imgRes);
                break;
            case TYPE_TWO_ROW_IMG_STR:
                setWidgetVisible(View.VISIBLE, View.GONE, View.VISIBLE);
                setVerticalContent(imgRes, toastBelowImgStr);
                break;
            default:
                break;
        }
    }


    /**
     * 当前已经有Toast显示时，那么只对其中的内容进行替换
     *
     * @param type type
     * @param drawable drawable
     * @param toastTextStr toastTextSt
     * @param toastBelowImgStr toastBelowImgSt
     */
    public void setContent(final int type,
                           final Drawable drawable,
                           final String toastTextStr,
                           final String toastBelowImgStr) {
        switch (type) {
            case TYPE_TOW_ROW_IMG_STR_STR:
                setHorizontalContent(drawable, toastTextStr, toastBelowImgStr);
                break;
            case TYPE_ONE_ROW_IMG_STR:
                setHorizontalContent(drawable, toastTextStr);
                break;
            case TYPE_ONE_ROW_STR:
                setHorizontalContent(toastTextStr);
                break;
            case TYPE_ONE_ROW_IMG:
                setHorizontalContent(drawable);
                break;
            case TYPE_TWO_ROW_IMG_STR:
                setWidgetVisible(View.VISIBLE, View.GONE, View.VISIBLE);
                setVerticalContent(drawable, toastBelowImgStr);
                break;
            default:
                break;
        }
    }

    /**
     * 两排显示：第一排图片加文字 ，第二排文字
     *
     */
    void setHorizontalContent(final int imgRes,
                                     final String toastTextStr,
                                     final String toastBelowImgStr) {
        setWidgetVisible(View.VISIBLE, View.VISIBLE, View.VISIBLE);
        if (null != toastImg) {
            toastImg.setImageResource(imgRes);
        }
        setText(toastText, toastTextStr);
        setText(toastTextBImg, toastBelowImgStr);
        setTextAndImgParams(10, true);
        show();
    }


    /**
     * 两排显示：第一排图片加文字 ，第二排文字
     *
     */
    private void setHorizontalContent(final Drawable drawable,
                                     final String toastTextStr,
                                     final String toastBelowImgStr) {
        setWidgetVisible(View.VISIBLE, View.VISIBLE, View.VISIBLE);
        if (null != toastImg && null != drawable) {
            toastImg.setImageDrawable(drawable);
        }
        setText(toastText, toastTextStr);
        setText(toastTextBImg, toastBelowImgStr);
        setTextAndImgParams(10, true);
        show();
    }

    /**
     * 一排显示图片和文字
     *
     */
    void setHorizontalContent(final int imgRes, final String toastTextStr) {
        setWidgetVisible(View.VISIBLE, View.VISIBLE, View.GONE);
        if (null != toastImg) {
            toastImg.setImageResource(imgRes);
        }
        setText(toastText, toastTextStr);
        setTextAndImgParams(10, false);
        show();
    }

    /**
     * 一排显示图片和文字
     *
     */
    private void setHorizontalContent(final Drawable drawable, final String toastTextStr) {
        setWidgetVisible(View.VISIBLE, View.VISIBLE, View.GONE);
        if (null != toastImg && null != drawable) {
            toastImg.setImageDrawable(drawable);
        }
        setText(toastText, toastTextStr);
        setTextAndImgParams(10, false);
        show();
    }

    /**
     * 只显示一排文字
     *
     */
    void setHorizontalContent(final String toastTextStr) {
        setWidgetVisible(View.GONE, View.VISIBLE, View.GONE);
        setText(toastText, toastTextStr);
        setToastTextLayoutParams(0, 0, 0, 0, RelativeLayout.CENTER_IN_PARENT);
        show();
    }

    /**
     * 只显示图片
     *
     */
    void setHorizontalContent(final int imgRes) {
        setWidgetVisible(View.VISIBLE, View.GONE, View.GONE);
        if (null != toastImg) {
            toastImg.setImageResource(imgRes);
        }
        setToastImgLayoutParams(0, 0, 0, 0, RelativeLayout.CENTER_IN_PARENT);
        show();
    }

    /**
     * 只显示图片
     *
     */
    private void setHorizontalContent(final Drawable drawable) {
        setWidgetVisible(View.VISIBLE, View.GONE, View.GONE);
        if (null != toastImg && null != drawable) {
            toastImg.setImageDrawable(drawable);
        }
        setToastImgLayoutParams(0, 0, 0, 0, RelativeLayout.CENTER_IN_PARENT);
        show();
    }

    /**
     * 竖排显示：上面图片，下面文字
     *
     */
    void setVerticalContent(final int imgRes,
                                   final String toastBelowImgStr) {
        setWidgetVisible(View.VISIBLE, View.GONE, View.VISIBLE);
        setText(toastTextBImg, toastBelowImgStr);
        if (null != toastImg) {
            toastImg.setImageResource(imgRes);
        }
        setToastImgLayoutParams(0, 0, 0, 10, RelativeLayout.CENTER_IN_PARENT);
        setFirstRowLayoutParams(RelativeLayout.CENTER_HORIZONTAL);
        show();
    }

    /**
     * 竖排显示：上面图片，下面文字
     *
     */
    private void setVerticalContent(final Drawable drawable,
                                   final String toastBelowImgStr) {
        setWidgetVisible(View.VISIBLE, View.GONE, View.VISIBLE);
        setText(toastTextBImg, toastBelowImgStr);
        if (null != toastImg && null != drawable) {
            toastImg.setImageDrawable(drawable);
        }
        setToastImgLayoutParams(0, 0, 0, 10, RelativeLayout.CENTER_IN_PARENT);
        setFirstRowLayoutParams(RelativeLayout.CENTER_HORIZONTAL);
        show();
    }


    // 第一排让图片永远在最左边，且永远相对于右侧文字垂直方向居中
    private void setTextAndImgParams(final int textLeftMargin, final boolean isTwoRow) {
        int textH = getViewWidthOrHeight(toastText, false);
        int imgH = getViewWidthOrHeight(toastImg, false);
        int imgW = getViewWidthOrHeight(toastImg, true);
        int maxH;
        if (textH <= imgH) {
            maxH = imgH;
            setToastTextLayoutParams(textLeftMargin + imgW, (imgH - textH) / 2,
                    0, (imgH - textH) / 2, RelativeLayout.ALIGN_RIGHT);
            setToastImgLayoutParams(0, 0, 0, 0, RelativeLayout.ALIGN_LEFT);
        } else {
            maxH = textH;
            setToastTextLayoutParams(textLeftMargin + imgW, 0, 0, 0,
                    RelativeLayout.ALIGN_RIGHT);
            setToastImgLayoutParams(0, (textH - imgH) / 2, 0,
                    (textH - imgH) / 2, RelativeLayout.ALIGN_LEFT);
        }

        //直接将toastTextBImg放在图片下面竟然不起作用，所以在有两排的地方做此处理
        if (isTwoRow) {
            RelativeLayout.LayoutParams blp;
            if (null != toastTextBImg && null != (blp = (RelativeLayout.LayoutParams) toastTextBImg.getLayoutParams())) {
                blp.setMargins(0, maxH + 10, 0, 0);
                toastTextBImg.setLayoutParams(blp);
            }
        }
        setFirstRowLayoutParams(RelativeLayout.ALIGN_LEFT);
    }

    // 设置第一排文字的LayoutParams
    private void setToastTextLayoutParams(final int l, final int t,
                                          final int r, final int b, final int verb) {
        if (null != toastText) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(l, t, r, b);
            params.addRule(verb);
            toastText.setLayoutParams(params);
        }
    }

    // 设置第一排图片的LayoutParams
    private void setToastImgLayoutParams(final int l, final int t, final int r,
                                         final int b, final int verb) {
        if (null != toastImg) {
            RelativeLayout.LayoutParams paramsImg = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsImg.addRule(verb);
            paramsImg.setMargins(l, t, r, b);
            toastImg.setLayoutParams(paramsImg);
        }
    }

    // 设置第一排的整体样式
    private void setFirstRowLayoutParams(final int verb) {
        if (null != firstRow) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(verb);
            firstRow.setLayoutParams(params);
        }
    }

    // 三个组件的可见性
    private void setWidgetVisible(final int toastImgVisible,
                                  final int toastTextVisible, final int toastBelowImgVisible) {
        setVisible(toastText, toastTextVisible);
        setVisible(toastImg, toastImgVisible);
        setVisible(toastTextBImg, toastBelowImgVisible);
    }

    private void setVisible(final View v, final int visible) {
        if (null != v) {
            v.setVisibility(visible);
        }
    }

    private void setText(final TextView t, final String s) {
        if (null != t && null != s) {
            t.setVisibility(View.VISIBLE);
            t.setText(s);
        }
    }

    private int getViewWidthOrHeight(final View v, final boolean isWidth) {
        int param = 0;
        if (null != v) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            v.measure(w, h);
            if (isWidth) {
                param = v.getMeasuredWidth();
            } else {
                param = v.getMeasuredHeight();
            }
        }
        return param;
    }

    private int getWidth(Context context) {
        if (null != context) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }
        return 0;
    }

    private int convertDIP2PX(Context context, float dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

}
