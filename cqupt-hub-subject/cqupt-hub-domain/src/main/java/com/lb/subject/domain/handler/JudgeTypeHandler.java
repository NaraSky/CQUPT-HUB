package com.lb.subject.domain.handler;

import com.lb.subject.common.enums.SubjectInfoTypeEnum;
import com.lb.subject.domain.entity.SubjectInfoBO;
import org.springframework.stereotype.Component;

@Component
public class JudgeTypeHandler implements SubjectTypeHandler{
    
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.JUDGE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
    }
}
