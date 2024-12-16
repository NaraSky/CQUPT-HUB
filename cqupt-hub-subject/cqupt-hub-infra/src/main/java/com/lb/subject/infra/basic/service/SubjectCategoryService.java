package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectCategory;

import java.util.List;

public interface SubjectCategoryService {

    /**
     * 新增数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    SubjectCategory insert(SubjectCategory subjectCategory);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectCategory queryById(Long id);

    /**
     * 修改数据
     *
     * @param subjectCategory 实例对象
     * @return 是否成功
     */
    int update(SubjectCategory subjectCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);


    /**
     * 查询分类
     *
     * @param subjectCategory 实例对象
     * @return 实例对象的集合
     */
    List<SubjectCategory> queryCategory(SubjectCategory subjectCategory);

    /**
     * 查询指定主题的数量
     *
     * @param id 主题的ID
     * @return 返回指定主题的数量，若未找到则返回null
     */
    Integer querySubjectCount(Long id);
}
