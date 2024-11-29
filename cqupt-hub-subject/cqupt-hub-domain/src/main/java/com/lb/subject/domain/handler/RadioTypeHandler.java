package com.lb.subject.domain.handler;

import com.lb.subject.common.enums.SubjectInfoTypeEnum;
import com.lb.subject.domain.convert.RadioSubjectConverter;
import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.infra.basic.entity.SubjectRadio;
import com.lb.subject.infra.basic.service.SubjectRadioService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
public class RadioTypeHandler implements SubjectTypeHandler {

    @Resource
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //单选题目的插入
        List<SubjectRadio> subjectRadioList = new LinkedList<>();
        subjectInfoBO.getOptionList().forEach(option -> {
            SubjectRadio subjectRadio = RadioSubjectConverter.INSTANCE.convertBOTOSubjectRadio(option);
            subjectRadio.setSubjectId(subjectInfoBO.getId());
            subjectRadioList.add(subjectRadio);
        });
        subjectRadioService.batchInsert(subjectRadioList);
    }
}
