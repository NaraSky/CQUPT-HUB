package com.lb.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectCategoryBO implements Serializable {

    private static final long serialVersionUID = -1509887431737431382L;

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
    private Integer categoryType;

    /**
     * 图标连接
     */
    private String imageUrl;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 数量
     */
    private Integer count;
}
