package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectLabelBO;
import com.lb.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelConverter {
    SubjectLabelConverter INSTANCE = Mappers.getMapper(SubjectLabelConverter.class);

    SubjectLabel convertBOTOSubjectLabel(SubjectLabelBO subjectLabelBO);

    List<SubjectLabelBO> convertSubjectLabelListTOBO(List<SubjectLabel> subjectLabelList);
}
