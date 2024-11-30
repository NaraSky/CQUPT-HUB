package com.lb.subject.infra.basic.service;

import com.lb.subject.infra.basic.entity.SubjectJudge;

import java.util.List;

public interface SubjectJudgeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectJudge queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectJudge 实例对象
     * @return 实例对象
     */
    SubjectJudge insert(SubjectJudge subjectJudge);

    /**
     * 修改数据
     *
     * @param subjectJudge 实例对象
     * @return 实例对象
     */
    SubjectJudge update(SubjectJudge subjectJudge);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件查询主题判定信息
     *
     * @param subjectJudge 包含查询条件的主题判定对象
     * @return 返回一个包含满足条件的主题判定信息列表
     */
    List<SubjectJudge> queryByCondition(SubjectJudge subjectJudge);

}
