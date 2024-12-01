package com.lb.subject.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubjectLabel implements Serializable {

    private static final long serialVersionUID = 6097631398871966294L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 标签分类
     */
    private String labelName;
    /**
     * 排序
     */
    private Integer sortNum;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 是否删除 0否 1是
     */
    private Integer isDeleted;
    /**
     * 更新时间
     */
    private Date updateTime;

}

