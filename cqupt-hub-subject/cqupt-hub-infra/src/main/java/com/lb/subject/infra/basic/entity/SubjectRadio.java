package com.lb.subject.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubjectRadio implements Serializable {

    private static final long serialVersionUID = -7604695986860271485L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * a,b,c,d
     */
    private Integer optionType;
    /**
     * 选项内容
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;
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
     * 是否删除 0:未删除 1:已删除
     */
    private Integer isDeleted;
}

