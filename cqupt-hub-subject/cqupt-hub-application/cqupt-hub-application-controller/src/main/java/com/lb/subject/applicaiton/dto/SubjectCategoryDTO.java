package com.lb.subject.applicaiton.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubjectCategoryDTO implements Serializable {

    private static final long serialVersionUID = 6892020339973969000L;

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

    /**
     * 标签信息
     */
    private List<SubjectLabelDTO> labelDTOList;

}
