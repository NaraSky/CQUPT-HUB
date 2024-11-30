package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectAnswerBO;
import com.lb.subject.infra.basic.entity.SubjectMultiple;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MultipleSubjectConverter {

    MultipleSubjectConverter INSTANCE = Mappers.getMapper(MultipleSubjectConverter.class);

    SubjectMultiple convertBOTOSubjectMultiple(SubjectAnswerBO subjectAnswerBO);

    List<SubjectAnswerBO> convertSubjectMultipleListTOAnswerBO(List<SubjectMultiple> subjectMultipleList);

}
