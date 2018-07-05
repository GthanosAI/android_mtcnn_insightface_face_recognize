package cn.com.earth.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.earth.R;

/**
 * 介绍: 顶部的头部
 * 作者: jacky
 * 邮箱: none
 * 时间:  2017/6/21 下午5:16
 */

public class AppToolBar extends RelativeLayout {
    private LinearLayout layout;
    private View view;
    private Context context;
    private int height;
    private LinearLayout left;
    private RelativeLayout centre;
    private LinearLayout right;

    private ImageView leftImg;
    private ImageView centreImg;
    private ImageView rightImg;

    private TextView leftText;
    private TextView centerText;
    private TextView rightText;

    private TextView leftFame;
    private TextView rightFame;
    private RelativeLayout customizedLayout;
    private RelativeLayout commonLayout;
    private View divider;

    public AppToolBar(Context context) {
        super(context);
        init(context);
    }

    public AppToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppToolBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public void init(final Context context) {
        if (null == context) {
            throw new IllegalArgumentException("TopBannerView creat: null params(context)");
        }
        this.context = context;
        view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.earth_app_tool_bar, this);
        // height = getViewWidthOrHeight(view, false);
        initView();
    }

    public void setBgColor(int color) {
        if (null != layout) {
            layout.setBackgroundColor(color);
        }
    }

    public ImageView getRightImage() {
        return rightImg;
    }

    private void initView() {
        if (null != view) {
            view.setMinimumHeight((int) context.getResources().getDimension(R.dimen.toolbar_height));
            layout = (LinearLayout) view.findViewById(R.id.app_top_banner_layout);
            left = (LinearLayout) view.findViewById(R.id.app_top_banner_left_layout);
            centre = (RelativeLayout) view.findViewById(R.id.app_top_banner_centre_layout);
            right = (LinearLayout) view.findViewById(R.id.app_top_banner_right_layout);
            leftImg = (ImageView) view.findViewById(R.id.app_top_banner_left_image);
            centreImg = (ImageView) view.findViewById(R.id.app_top_banner_center_image);
            rightImg = (ImageView) view.findViewById(R.id.app_top_banner_right_image);
            leftText = (TextView) view.findViewById(R.id.app_top_banner_left_text);
            centerText = (TextView) view.findViewById(R.id.app_top_banner_center_text);
            rightText = (TextView) view.findViewById(R.id.app_top_banner_right_text);
            leftFame = (TextView) view.findViewById(R.id.app_top_banner_left_fame);
            rightFame = (TextView) view.findViewById(R.id.app_top_banner_right_fame);
            divider = view.findViewById(R.id.divider);

            customizedLayout = (RelativeLayout) view.findViewById(R.id.custom_layout);
            commonLayout = (RelativeLayout) view.findViewById(R.id.common_layout);

            setCustom(false);

            setLeftVisible(false);
            setCenterVisible(false);
            setRightVisible(false);
        }
    }


    public void setBackColor(int color) {
        if (null != layout) {
            layout.setBackgroundDrawable(null);
            layout.setBackgroundColor(color);
        }
    }

    /**
     * 设置中间，调用时默认将传递不为空的显示
     *
     * @param imgResId
     * @param s
     * @param l
     */
    public void setCenter(final Integer imgResId, final String s, final OnClickListener l) {
        setCustom(false);
        setCenterVisible(true);
        setImgRes(centreImg, imgResId);
        setText(centerText, s);
        setOnClickListener(l);
        setCenter();
    }

    /**
     * 设置左侧，调用时默认将传递不为空的显示
     *
     * @param imgResId
     * @param s
     * @param l
     */
    public void setLeft(final Integer imgResId, final String s, final OnClickListener l) {
        setCustom(false);
        setLeftVisible(true);
        setImgRes(leftImg, imgResId);
        setText(leftText, s);
        setOnClickListener(left, l);
        setCenter();
    }

    /**
     * 设置右侧，调用时默认将传递不为空的显示
     *
     * @param imgResId
     * @param s
     * @param l
     */
    public void setRight(final Integer imgResId, final String s, final OnClickListener l) {
        setCustom(false);
        setRightVisible(true);
        setImgRes(rightImg, imgResId);
        setText(rightText, s);
        setOnClickListener(right, l);
        setCenter();
    }

    /**
     * 设置右侧，调用时默认将传递不为空的显示
     *
     * @param imgResId
     */
    public void setRightImage(final Integer imgResId) {
        setCustom(false);
        setRightVisible(true);
        setImgRes(rightImg, imgResId);
        setCenter();
    }


    private void setVisible(final View v, final boolean visible) {
        if (null != v) {
            if (visible) {
                v.setVisibility(View.VISIBLE);
            } else {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setCenterVisible(final boolean visible) {
        setVisible(centre, visible);
    }

    public void setLeftVisible(final boolean visible) {
        setVisible(left, visible);
    }

    public void setRightVisible(final boolean visible) {
        setVisible(right, visible);
    }

    private void setImgRes(final ImageView img, final Integer imgResId) {
        if (null != img) {
            if (null != imgResId) {
                img.setImageResource(imgResId);
//                int imgH = getViewWidthOrHeight(img, false);
//                int h = height - DisplayUtils.dip2px(context, 30);
                img.setVisibility(View.VISIBLE);
            } else {
                img.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setCenterTextColor(int color) {
        if (null != centerText) {
            centerText.setTextColor(color);
        }
    }

    public void setLeftTextColor(int color) {
        if (null != leftText) {
            leftText.setTextColor(color);
        }
    }

    public void setRightTextColor(int color) {
        if (null != rightText) {
            rightText.setTextColor(color);
        }
    }

    public void addToRight(View view) {
        if (view != null) {
            right.setVisibility(VISIBLE);
            right.removeAllViewsInLayout();
            right.addView(view);

            view.setVisibility(View.VISIBLE);
            setCenter();
        }
    }


    public void addToCenter(View view) {
        if (null != view) {
            centre.setVisibility(VISIBLE);
            centre.removeAllViewsInLayout();
            centre.addView(view);
            setCenter();
        }
    }


    public void addToLeft(View view) {
        if (view != null) {
            left.setVisibility(VISIBLE);
            left.removeAllViewsInLayout();
            left.addView(view);
//            int imgH = getViewWidthOrHeight(view, false);
//            int h = height - DisplayUtils.dip2px(30);
//            if (imgH > h && h > 0) {
//                float scale = h * 1f / height;
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
//                lp.width = (int) ((getViewWidthOrHeight(view, true) * scale));
//                lp.height = h;
//            }
            view.setVisibility(View.VISIBLE);
            setCenter();
        }
    }

    private void setText(final TextView t, final String s) {
        if (null != t) {
            if (!TextUtils.isEmpty(s)) {
                t.setVisibility(View.VISIBLE);
                t.setText(s);
            } else {
                t.setVisibility(View.GONE);
            }
        }
    }

    private void setOnClickListener(final View v, final OnClickListener l) {
        if (null != v) {
            v.setOnClickListener(l);
        }
    }

    //对于有可能顶部左右布局宽度不同时使用
    private void setCenter() {
        if (null != view) {
            int lw = getViewWidthOrHeight(left, true);
            int rw = getViewWidthOrHeight(right, true);
            int maxW = getMaxWidth(lw, rw);

            if (null != rightFame) {
                LayoutParams paramsR = new LayoutParams(maxW, LayoutParams.WRAP_CONTENT);
                paramsR.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                rightFame.setLayoutParams(paramsR);
            }

            if (null != leftFame) {
                LayoutParams paramsL = new LayoutParams(maxW, LayoutParams.WRAP_CONTENT);
                paramsL.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                leftFame.setLayoutParams(paramsL);
            }
        }
    }

    private int getMaxWidth(int lw, int rw) {
        if (lw > rw) {
            return lw;
        } else {
            return rw;
        }
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public RelativeLayout getCentre() {
        return centre;
    }

    public TextView getRightText() {
        return rightText;
    }

    public TextView getCenterText() {
        return centerText;
    }

    public TextView getLeftText() {
        return leftText;
    }


    public ViewGroup getLeftLayout() {
        return left;
    }

    public RelativeLayout getCentreLayout() {
        return centre;
    }

    public ViewGroup getRightLayout() {
        return right;
    }

    int getViewWidthOrHeight(final View v, final boolean isWidth) {
        int param = 0;
        if (null != v) {
            int w = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int h = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            v.measure(w, h);
            if (isWidth) {
                param = v.getMeasuredWidth();
            } else {
                param = v.getMeasuredHeight();
            }
        }
        return param;
    }


    public void setLayoutOnClickListener(OnClickListener l) {
        if (null != layout) {
            layout.setOnClickListener(l);
        }
    }

    private void setCustom(boolean isCustom) {
        if (isCustom) {
            customizedLayout.setVisibility(VISIBLE);
            commonLayout.setVisibility(GONE);
        } else {
            customizedLayout.setVisibility(GONE);
            commonLayout.setVisibility(VISIBLE);
        }
    }

    public void setCustomTopLayout(View view) {
        setCustom(true);
        customizedLayout.removeAllViews();
        customizedLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setDividerVisible(boolean visible) {
        if (visible) {
            divider.setVisibility(View.VISIBLE);
        } else {
            divider.setVisibility(View.GONE);
        }
    }


    public static class AppToolbarConfig {
        String centerText;
        int leftImg;
        String leftText;
        String rightText;
        OnClickListener leftAction;
        OnClickListener centerAction;
        OnClickListener rightAction;
        boolean isDividerVisible;

        int topbgColor = -1;

        public AppToolbarConfig setTopbgColor(int topbgColor) {
            this.topbgColor = topbgColor;
            return this;
        }

        public int getTopbgColor() {
            return topbgColor;
        }

        public AppToolbarConfig() {
            this.isDividerVisible = true;
        }

        public AppToolbarConfig setCenterText(String centerText) {
            this.centerText = centerText;
            return this;
        }

        public AppToolbarConfig setLeftImg(int leftImg) {
            this.leftImg = leftImg;
            return this;
        }

        public AppToolbarConfig setRightText(String rightText) {
            this.rightText = rightText;
            return this;
        }

        public AppToolbarConfig setLeftAction(OnClickListener leftAction) {
            this.leftAction = leftAction;
            return this;
        }

        public AppToolbarConfig setCenterAction(OnClickListener centerAction) {
            this.centerAction = centerAction;
            return this;
        }

        public AppToolbarConfig setRightAction(OnClickListener rightAction) {
            this.rightAction = rightAction;
            return this;
        }

        public String getCenterText() {
            return centerText;
        }

        public int getLeftImg() {
            return leftImg;
        }

        public String getRightText() {
            return rightText;
        }

        public OnClickListener getLeftAction() {
            return leftAction;
        }

        public OnClickListener getCenterAction() {
            return centerAction;
        }

        public OnClickListener getRightAction() {
            return rightAction;
        }

        public boolean isDividerVisible() {
            return isDividerVisible;
        }

        public AppToolbarConfig setDividerVisible(boolean dividerVisible) {
            isDividerVisible = dividerVisible;
            return this;
        }

        public String getLeftText() {
            return leftText;
        }

        public AppToolbarConfig setLeftText(String leftText) {
            this.leftText = leftText;
            return this;
        }
    }

    public void setConfig(AppToolbarConfig config) {
        if (config.getLeftImg() > 0) {
            setLeft(config.getLeftImg(), null, config.getLeftAction());
        } else if (config.getLeftText() != null) {
            setLeft(null, config.getLeftText(), config.getLeftAction());
        }

        if (config.getCenterText() != null) {
            setCenter(null, config.getCenterText(), config.getCenterAction());
        }

        if (config.getRightText() != null) {
            setRight(null, config.getRightText(), config.getRightAction());
        }

        if (config.getTopbgColor() > 0) {
            setBackColor(config.getTopbgColor());
        }

        setDividerVisible(config.isDividerVisible);
    }

}