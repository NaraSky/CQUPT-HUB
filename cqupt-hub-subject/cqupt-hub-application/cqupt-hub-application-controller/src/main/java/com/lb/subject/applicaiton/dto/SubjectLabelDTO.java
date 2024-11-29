package com.lb.subject.applicaiton.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectLabelDTO implements Serializable {

    private static final long serialVersionUID = -6215318183835188280L;

    /**
     * 主键
     */
    private Long id;
    
    /**
     * 分类id
     */
    private Long categoryId;
    
    /**
     * 标签分类
     */
    private String labelName;

    /**
     * 排序
     */
    private Integer sortNum;
}
