package com.lb.subject.domain.service;

import com.lb.subject.common.entity.PageResult;
import com.lb.subject.domain.entity.SubjectInfoBO;

public interface SubjectInfoDomainService {

    /**
     * 新增题目
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 分页查询
     */
    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);

}
