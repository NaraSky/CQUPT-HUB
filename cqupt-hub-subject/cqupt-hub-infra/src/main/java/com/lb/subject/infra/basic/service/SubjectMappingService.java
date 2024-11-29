package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectMapping;

import java.util.List;

public interface SubjectMappingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectMapping queryById(int id);

    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    SubjectMapping insert(SubjectMapping subjectMapping);

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 影响行数
     */
    int update(SubjectMapping subjectMapping);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(int id);

    /**
     * 查询所有数据
     *
     * @param subjectMapping 查询条件
     * @return 对象列表
     */
    List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping);

}
