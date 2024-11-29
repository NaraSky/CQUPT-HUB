package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectAnswerBO;
import com.lb.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RadioSubjectConverter {
    RadioSubjectConverter INSTANCE = Mappers.getMapper(RadioSubjectConverter.class);

    SubjectRadio convertBOTOSubjectRadio(SubjectAnswerBO subjectAnswerBO);
}
