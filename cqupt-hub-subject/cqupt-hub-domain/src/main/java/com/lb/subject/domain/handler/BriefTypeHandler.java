package com.lb.subject.domain.handler;

import com.lb.subject.common.enums.SubjectInfoTypeEnum;
import com.lb.subject.domain.entity.SubjectInfoBO;
import org.springframework.stereotype.Component;

@Component
public class BriefTypeHandler implements SubjectTypeHandler{
    
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {

    }
}
