package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.infra.basic.entity.SubjectBrief;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BriefSubjectConverter {

    BriefSubjectConverter INSTANCE = Mappers.getMapper(BriefSubjectConverter.class);

    SubjectBrief convertBOTOSubjectBrief(SubjectInfoBO subjectInfoBO);

}
