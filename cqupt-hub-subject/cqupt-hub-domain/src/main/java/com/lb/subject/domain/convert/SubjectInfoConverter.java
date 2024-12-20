package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.domain.entity.SubjectOptionBO;
import com.lb.subject.infra.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoConverter {
    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    SubjectInfoBO convertOptionTOSubjectInfoBO(SubjectOptionBO subjectOptionBO);

    SubjectInfo convertBOTOSubjectInfo(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoBO> convertSubjectInfoListTOBO(List<SubjectInfo> subjectInfoList);

    SubjectInfoBO convertOptionAndInfoTOBO(SubjectOptionBO subjectOptionBO,SubjectInfo subjectInfo);
}
