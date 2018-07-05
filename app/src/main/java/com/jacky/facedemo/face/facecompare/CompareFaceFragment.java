package com.jacky.facedemo.face.facecompare;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gthanos.mface.FaceEngineManager;
import com.jacky.facedemo.R;
import com.jacky.facedemo.base.AsyncProcessor;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import java.util.ArrayList;

import cn.com.earth.base.BaseFragmentWithToolBar;
import cn.com.earth.tools.BitmapUtils;
import cn.com.earth.tools.LoadImageUtils;
import cn.com.earth.widget.AppToolBar;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/4 上午11:02
 */

public class CompareFaceFragment extends BaseFragmentWithToolBar {

    CompareParam compareParam;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_compare_face;
    }

    @Override
    protected void bindDataAndListener() {
        compareParam = new CompareParam();
        findViewById(R.id.ivLeft).setOnClickListener(v -> {
            onSelect(true);
        });

        findViewById(R.id.ivRight).setOnClickListener(v -> {
            onSelect(false);
        });

        Album.initialize(AlbumConfig.newBuilder(mActivity)
                .setAlbumLoader(new MediaLoader())
                .build());

        findViewById(R.id.btnCompare).setOnClickListener(v -> {

            if (!compareParam.isValid()) {
                cn.com.earth.tools.toast.Toast.showWarning("请选择正确的照片");
                return;
            }

            AsyncProcessor.getInstance().execute(() -> {

                long t1 = System.currentTimeMillis();

                Bitmap b1 = compareParam.decodeLeft();
                Bitmap b2 = compareParam.decodeRight();

                Log.e("----time debug---decode", (System.currentTimeMillis() - t1) + "");

                float diff = FaceEngineManager.getInstance().compare(b1, b2);

                handler.post(() -> showMsg(String.format("相似度  %.2f", diff * 100) + "%"));
            });
        });
    }

    private void showMsg(String msg) {
        TextView view = findViewById(R.id.tvMsg);
        view.setText(msg);
    }

    private void onSelect(boolean left) {
        ArrayList<AlbumFile> checked = new ArrayList<>();
        Album.image(mActivity) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(2)
                .checkedList(checked)
                .afterFilterVisibility(false) // Show the filtered files, but they are not available.
                .onResult(result -> {


                    if (!result.isEmpty()) {

                        if (result.size() == 1) {

                            ImageView view = left ? findViewById(R.id.ivLeft) : findViewById(R.id.ivRight);

                            if (left) {
                                compareParam.setLetf(result.get(0).getPath());
                            } else {
                                compareParam.setRight(result.get(0).getPath());
                            }
                            LoadImageUtils.loadCircleAvatar(view, result.get(0).getPath());
                        } else {

                            compareParam.setLetf(result.get(0).getPath()).setRight(result.get(1).getPath());
                            LoadImageUtils.loadCircleAvatar(findViewById(R.id.ivLeft), result.get(0).getPath());
                            LoadImageUtils.loadCircleAvatar(findViewById(R.id.ivRight), result.get(1).getPath());
                        }
                    }


                })
                .onCancel(result -> {
                })
                .start();
    }

    @Override
    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return new AppToolBar.AppToolbarConfig()
                .setCenterText("比较")
                .setLeftText("返回")
                .setLeftAction(v -> finishAll());

    }


    public static class MediaLoader implements AlbumLoader {

        @Override
        public void load(ImageView imageView, AlbumFile albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            LoadImageUtils.loadIcon(imageView, url);
        }
    }

    class CompareParam {
        String letf;
        String right;

        public String getLetf() {
            return letf;
        }

        public CompareParam setLetf(String letf) {
            this.letf = letf;
            return this;
        }

        public boolean isValid() {
            return !TextUtils.isEmpty(letf) && !TextUtils.isEmpty(right);
        }

        public String getRight() {
            return right;
        }

        public CompareParam setRight(String right) {
            this.right = right;
            return this;
        }

        public Bitmap decodeLeft() {
            return BitmapUtils.downSampleBitmap(letf, 1080, 1080);
        }

        public Bitmap decodeRight() {
            return BitmapUtils.downSampleBitmap(right, 1080, 1080);

        }
    }
}
