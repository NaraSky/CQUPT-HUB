package com.lb.subject.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubjectMapping implements Serializable {

    private static final long serialVersionUID = -72034591678118838L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 标签id
     */
    private Long labelId;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除 0否 1是
     */
    private Integer isDeleted;
}
