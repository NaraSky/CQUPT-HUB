package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.infra.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectInfoConverter {
    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    SubjectInfo convertBOTOSubjectInfo(SubjectInfoBO subjectInfoBO);
}
