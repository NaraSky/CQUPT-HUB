package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectMultiple;
import com.lb.subject.infra.basic.entity.SubjectRadio;

import java.util.List;

public interface SubjectRadioService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectRadio queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    SubjectRadio insert(SubjectRadio subjectRadio);

    /**
     * 批量插入
     */
    void batchInsert(List<SubjectRadio> subjectRadioList);

    /**
     * 修改数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    SubjectRadio update(SubjectRadio subjectRadio);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据给定条件查询多个主题信息
     *
     * @param subjectRadio 包含查询条件的主题对象
     * @return 返回一个包含查询结果的多个主题信息列表
     */
    List<SubjectRadio> queryByCondition(SubjectRadio subjectRadio);

}
