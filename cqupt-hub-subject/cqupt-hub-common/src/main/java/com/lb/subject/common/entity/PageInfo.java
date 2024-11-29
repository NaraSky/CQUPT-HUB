package com.lb.subject.common.entity;

public class PageInfo {
    private Integer pageNum = 1;
    private Integer pageSize = 20;

    // 获取当前页码
    public Integer getPageNum() {
        // 如果页码为空或小于1，则将页码设置为1
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        return pageNum;
    }

    // 获取每页显示的记录数
    public Integer getPageSize() {
        // 如果每页显示记录数为空、小于1或大于最大值，则返回默认值20
        if (pageSize == null || pageSize < 1 || pageSize > Integer.MAX_VALUE) {
            return 20;
        }
        return pageSize;
    }
}
