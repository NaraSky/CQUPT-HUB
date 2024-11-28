package com.lb.subject.infra.basic.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目分类
 * subject_category
 */
@Data
public class SubjectCategory implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类类型
     */
    private Byte categoryType;

    /**
     * 图标连接
     */
    private String imageUrl;

    /**
     * 父级id
     */
    private Long parentId;

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
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0: 未删除 1: 已删除
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}