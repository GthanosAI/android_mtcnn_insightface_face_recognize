package com.jacky.facedemo.widget;

/**
 * 介绍:
 * 作者: jacky
 * 时间: 2018/7/3 下午7:49
 */

public class YuvData {
    public byte[] buf;
    int degree;
    int dataFromat;
    boolean isMirror;
    int height;
    int width;

    public byte[] getBuf() {
        return buf;
    }

    public int getDegree() {
        return degree;
    }

    public YuvData setDegree(int degree) {
        this.degree = degree;
        return this;
    }

    public int getDataFromat() {
        return dataFromat;
    }

    public YuvData setDataFromat(int dataFromat) {
        this.dataFromat = dataFromat;
        return this;
    }

    public boolean isMirror() {
        return isMirror;
    }

    public YuvData setMirror(boolean mirror) {
        isMirror = mirror;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public YuvData setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public YuvData setWidth(int width) {
        this.width = width;
        return this;
    }

    public YuvData(byte[] buf, int degree, int dataFromat, boolean isMirror, int width, int height) {
        this.buf = buf;
        this.degree = degree;
        this.dataFromat = dataFromat;
        this.isMirror = isMirror;
        this.height = height;
        this.width = width;
    }
}
