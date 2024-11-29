package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectInfo;

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

}
