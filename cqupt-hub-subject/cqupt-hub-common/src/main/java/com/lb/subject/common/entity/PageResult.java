package com.lb.subject.common.entity;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 276236891529458125L;

    private Integer pageNo = 1;

    private Integer pageSize = 20;

    private Integer total = 0;

    private Integer totalPages = 0;

    private List<T> result = Collections.emptyList();

    private Integer start = 1;

    private Integer end = 0;

    // 设置当前页码
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    // 设置每页显示的记录数
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(Integer total) {
        if (total == null || total < 0) {
            throw new IllegalArgumentException("Total must be a non-null positive integer.");
        }

        this.total = total;

        if (this.pageSize <= 0) {
            this.totalPages = 0;
            this.start = 0;
            this.end = 0;
            return;
        }

        // 计算总页数
        this.totalPages = (total + pageSize - 1) / pageSize; // 简化计算方式，同时保证向上取整

        // 计算当前页的记录起始位置
        this.start = (pageNo - 1) * pageSize + 1;

        // 保证start不小于1
        this.start = Math.max(this.start, 1);

        // 计算当前页的记录结束位置
        this.end = Math.min(this.start + pageSize - 1, total);
    }


    // 设置查询结果列表，并根据结果列表设置总记录数
    public void setRecords(List<T> result) {
        this.result = result;
        if (result != null && result.size() > 0) {
            setTotal(result.size());
        }
    }
}
