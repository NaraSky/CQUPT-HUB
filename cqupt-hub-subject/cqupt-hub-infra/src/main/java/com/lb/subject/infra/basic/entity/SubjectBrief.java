package com.lb.subject.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubjectBrief implements Serializable {

    private static final long serialVersionUID = -7816033987193343986L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Integer subjectId;
    /**
     * 题目答案
     */
    private String subjectAnswer;
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
     * 是否删除 0否 1是
     */
    private Integer isDeleted;
}
