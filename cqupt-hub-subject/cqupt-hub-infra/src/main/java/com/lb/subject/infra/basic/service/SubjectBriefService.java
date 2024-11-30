package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectBrief;
import com.lb.subject.infra.basic.entity.SubjectRadio;

import java.util.List;

public interface SubjectBriefService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectBrief queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectBrief 实例对象
     * @return 实例对象
     */
    SubjectBrief insert(SubjectBrief subjectBrief);

    /**
     * 修改数据
     *
     * @param subjectBrief 实例对象
     * @return 实例对象
     */
    SubjectBrief update(SubjectBrief subjectBrief);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件查询主题摘要信息
     *
     * @param subjectBrief 主题摘要对象，包含查询条件
     * @return 满足条件的主题摘要对象
     */
    SubjectBrief queryByCondition(SubjectBrief subjectBrief);

}
