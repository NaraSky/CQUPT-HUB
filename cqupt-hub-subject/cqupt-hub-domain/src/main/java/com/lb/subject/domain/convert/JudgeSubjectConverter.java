package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectAnswerBO;
import com.lb.subject.infra.basic.entity.SubjectJudge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JudgeSubjectConverter {

    JudgeSubjectConverter INSTANCE = Mappers.getMapper(JudgeSubjectConverter.class);

    List<SubjectAnswerBO> convertSubjectListTOAnswerBO(List<SubjectJudge> subjectJudgeList);

}
