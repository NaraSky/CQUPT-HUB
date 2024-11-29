package com.lb.subject.domain.handler;

import com.lb.subject.common.enums.SubjectInfoTypeEnum;
import com.lb.subject.domain.entity.SubjectInfoBO;

public interface SubjectTypeHandler {
    /**
     * 获取处理器类型
     *
     * @return
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 实际题目的插入
     *
     * @param subjectInfoBO
     */
    void add(SubjectInfoBO subjectInfoBO);
}
