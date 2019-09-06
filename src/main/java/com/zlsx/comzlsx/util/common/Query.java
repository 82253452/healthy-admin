package com.zlsx.comzlsx.util.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : houxm
 * @date : 2018/12/21 16:52
 * @description :查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    //当前页码
    private int pageIndex = 1;
    //每页条数
    private int pageSize = 10;

    public Query() {
    }

    public Query(Map<String, Object> params) {
        this.putAll(params);
        if (params.get("page") != null) {
            this.setPageIndex(Integer.parseInt(params.get("page").toString()));
        }
        if (params.get("limit") != null) {
            this.setPageSize(Integer.parseInt(params.get("limit").toString()));
        }
        //分页参数
        if (params.get("pageIndex") != null) {
            this.setPageIndex(Integer.parseInt(params.get("pageIndex").toString()));
        }
        if (params.get("pageSize") != null) {
            this.setPageSize(Integer.parseInt(params.get("pageSize").toString()));
        }
        this.remove("page");
        this.remove("limit");
        this.remove("pageIndex");
        this.remove("pageSize");
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
