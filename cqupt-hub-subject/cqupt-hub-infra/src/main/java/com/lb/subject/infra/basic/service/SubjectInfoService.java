package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectInfo;

import java.util.List;

public interface SubjectInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectInfo queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectInfo 实例对象
     * @return 实例对象
     */
    SubjectInfo insert(SubjectInfo subjectInfo);

    /**
     * 修改数据
     *
     * @param subjectInfo 实例对象
     * @return 实例对象
     */
    SubjectInfo update(SubjectInfo subjectInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件统计符合条件的记录数
     *
     * @param subjectInfo 包含查询条件的SubjectInfo对象
     * @param categoryId 类别ID，用于筛选符合条件的记录
     * @param labelId 标签ID，用于筛选符合条件的记录
     * @return 符合条件的记录数
     */
    int countByCondition(SubjectInfo subjectInfo, Long categoryId, Long labelId);

    /**
     * 根据条件分页查询学科信息
     *
     * @param subjectInfo 包含查询条件的学科信息对象
     * @param categoryId 类别ID，用于筛选符合条件的学科信息
     * @param labelId 标签ID，用于筛选符合条件的学科信息
     * @param start 分页查询的起始位置
     * @param pageSize 每页显示的记录数
     * @return 分页查询到的学科信息列表
     */
    List<SubjectInfo> queryPage(SubjectInfo subjectInfo, Long categoryId, Long labelId, int start, Integer pageSize);


}
