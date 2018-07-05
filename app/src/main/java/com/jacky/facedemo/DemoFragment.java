package com.jacky.facedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gthanos.mface.FaceEngineManager;
import com.gthanos.mface.FaceFileConfig;
import com.gthanos.mface.demo.FaceEditor;
import com.gthanos.mface.model.PersonModel;
import com.gthanos.mface.model.SearchResult;

import java.util.Arrays;
import java.util.List;

import cn.com.earth.base.BaseFragmentWithToolBar;
import cn.com.earth.tools.logger.LogUtils;
import cn.com.earth.widget.AppToolBar;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/6/25 下午1:52
 */

public class DemoFragment extends BaseFragmentWithToolBar {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindDataAndListener() {
        findViewById(R.id.btn_start).setOnClickListener(v -> compare());

        findViewById(R.id.btn_add).setOnClickListener(v -> input());

        findViewById(R.id.btn_search).setOnClickListener(v -> search());
    }

    private void compare() {
        Flowable.just(1)
                .map(new Function<Integer, List<Param>>() {
                    @Override
                    public List<Param> apply(Integer param) throws Exception {
                        DemoFragment.Param[] testImage = DataSet.testImage;
                        List<Param> list = Arrays.asList(testImage);
                        return list;
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List<Param>>() {
                    @Override
                    public void accept(List<Param> params) throws Exception {

                        for (Param param : params) {

                            param.decode();

                            Bitmap bitmap = param.getBitmap1();
                            FaceEngineManager.getInstance().compare(bitmap, param.bitmap2);

                            param.clear();
                        }

                    }
                });
    }

    @Override
    protected AppToolBar.AppToolbarConfig getAppToolBarConfig() {
        return new AppToolBar.AppToolbarConfig().setCenterText("测试")
                .setLeftText("返回")
                .setLeftAction(v -> finishAll());
    }


    public void input() {

        new Thread(() -> {

            for (int i = 0; i < DataSet.personOriginData.length; i++) {
                PersonOriginData data = DataSet.personOriginData[i];
                PersonModel personModel = FaceEditor.getInstance().newPersonModel();
                personModel.modifyName(data.getName());
                personModel.setSourceName(FaceFileConfig.FACE_LIB_DIR);
                List<String> files = data.getImgs();
                for (String file : files) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file);
                    FaceEditor.getInstance().addFace(bitmap);
                }

                personModel.dump();
                personModel.release();
            }


        }).start();
    }

    public void search() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (String file : DataSet.targetImages) {
                    LogUtils.e("test file ---" + file);
                    Bitmap bitmap = BitmapFactory.decodeFile(file);
                    SearchResult result = new SearchResult();
                    boolean ret = FaceEngineManager.getInstance().search(bitmap, FaceFileConfig.FACE_PERSON_DIR, result);
                    bitmap.recycle();
                }
            }
        }).start();
    }

    static class Param {
        String img1;
        String img2;

        Bitmap bitmap1;
        Bitmap bitmap2;

        public String getImg1() {
            return img1;
        }

        public Param setImg1(String img1) {
            this.img1 = img1;
            return this;
        }

        public String getImg2() {
            return img2;
        }

        public Param setImg2(String img2) {
            this.img2 = img2;
            return this;
        }

        public Bitmap getBitmap1() {
            return bitmap1;
        }

        public Param setBitmap1(Bitmap bitmap1) {
            this.bitmap1 = bitmap1;
            return this;
        }

        public Bitmap getBitmap2() {
            return bitmap2;
        }

        public Param setBitmap2(Bitmap bitmap2) {
            this.bitmap2 = bitmap2;
            return this;
        }

        public void decode() {
            bitmap1 = BitmapFactory.decodeFile(img1);
            bitmap2 = BitmapFactory.decodeFile(img2);
        }

        public void clear() {
            bitmap1.recycle();
            bitmap2.recycle();
        }
    }
}
