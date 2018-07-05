package cn.com.earth.net.basemodel;

import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/1/3 下午11:16
 */

public interface IDataModel<E> {
    List<E> getList();

    int getTotal();

    int getCurrentPage();

    int getCurrentPageSize();

    String getNt();

    boolean isEmpty();
}
