package com.lb.subject.applicaiton.converter;

import com.lb.subject.applicaiton.dto.SubjectInfoDTO;
import com.lb.subject.domain.entity.SubjectInfoBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectInfoDTOConverter {

    SubjectInfoDTOConverter INSTANCE = Mappers.getMapper(SubjectInfoDTOConverter.class);

    SubjectInfoBO convertDTOTOSubjectBO(SubjectInfoDTO subjectInfoDTO);

    SubjectInfoDTO convertBOTOSubjectInfoDTO(SubjectInfoBO subjectInfoBO);
}
