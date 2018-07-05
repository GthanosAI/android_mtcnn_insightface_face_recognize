package cn.com.earth.net.basemodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 介绍:  带有列表的数据模型泛型结构
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/1/3 下午11:19
 */

public class BaseModelListBean<T> {
    @SerializedName("list")
    private List<T> list;   // 瀑布式

    @SerializedName("total")  //分页式
    private int total;//总数。
    @SerializedName("page_count")
    private int pageCount;    //页数
    @SerializedName("pageSize")
    private int pageSize;    //每页数量
    @SerializedName("page_no")
    private int pageNo;    //当前页面号
    @SerializedName("count")
    private int count;//
    @SerializedName("nt")
    private String nt;    //版本Id，用作下一页版本号，null表示没有下一页
    @SerializedName("pt")
    private String pt;    //版本Id，用作上一页版本号

    public List<T> getList() {
        if (list != null) {
            return list;
        }
        return null;
    }

    public int getTotal() {
        return count == 0 ? total : count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getCount() {
        return count == 0 ? total : count;
    }

    public String getNt() {
        return nt;
    }

    public Long getTypeLongNt() {
        if (nt == null) {
            return null;
        } else {
            return Long.valueOf(nt);
        }
    }


    public Long getTypeLongPt() {
        if (pt == null) {
            return null;
        } else {
            return Long.valueOf(pt);
        }
    }

    public String getPt() {
        return pt;
    }


    public boolean isDataEmpty() {
        return getList() == null || getList().size() == 0;
    }
}
