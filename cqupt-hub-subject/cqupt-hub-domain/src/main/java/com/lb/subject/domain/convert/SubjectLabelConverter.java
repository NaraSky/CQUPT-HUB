package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectLabelBO;
import com.lb.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectLabelConverter {
    SubjectLabelConverter INSTANCE = Mappers.getMapper(SubjectLabelConverter.class);

    SubjectLabel convertBOTOSubjectLabel(SubjectLabelBO subjectLabelBO);
}
