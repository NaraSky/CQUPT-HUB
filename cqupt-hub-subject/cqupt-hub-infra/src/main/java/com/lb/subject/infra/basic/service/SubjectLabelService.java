package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectLabel;

import java.util.List;

public interface SubjectLabelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectLabel queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    int insert(SubjectLabel subjectLabel);

    /**
     * 修改数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    int update(SubjectLabel subjectLabel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 批量查询
     *
     * @param labelIdList 标签列表
     * @return 标签列表
     */
    List<SubjectLabel> batchQueryById(List<Long> labelIdList);

}
