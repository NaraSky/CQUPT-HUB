package com.lb.subject.applicaiton.converter;

import com.lb.subject.applicaiton.dto.SubjectAnswerDTO;
import com.lb.subject.domain.entity.SubjectAnswerBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectAnswerDTOConverter {

    SubjectAnswerDTOConverter INSTANCE = Mappers.getMapper(SubjectAnswerDTOConverter.class);

    SubjectAnswerBO convertDTOTOSubjectAnswerBO(SubjectAnswerDTO subjectAnswerDTO);

    List<SubjectAnswerBO> convertDTOTOBOList(List<SubjectAnswerDTO> subjectAnswerDTOList);

}
