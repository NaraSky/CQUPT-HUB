package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectMultiple;

import java.util.List;

public interface SubjectMultipleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectMultiple queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectMultiple 实例对象
     * @return 实例对象
     */
    SubjectMultiple insert(SubjectMultiple subjectMultiple);

    /**
     * 修改数据
     *
     * @param subjectMultiple 实例对象
     * @return 实例对象
     */
    SubjectMultiple update(SubjectMultiple subjectMultiple);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 批量插入
     *
     * @param subjectMultipleList
     */
    void batchInsert(List<SubjectMultiple> subjectMultipleList);

    /**
     * 根据条件查询多个主题信息
     *
     * @param subjectMultiple 包含查询条件的主题对象
     * @return 包含查询结果的多个主题信息列表
     */
    List<SubjectMultiple> queryByCondition(SubjectMultiple subjectMultiple);
}
