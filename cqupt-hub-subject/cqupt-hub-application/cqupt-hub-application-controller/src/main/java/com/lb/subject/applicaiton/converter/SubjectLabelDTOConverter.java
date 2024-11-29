package com.lb.subject.applicaiton.converter;

import com.lb.subject.applicaiton.dto.SubjectLabelDTO;
import com.lb.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelDTOConverter {
    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);

    SubjectLabelBO convertDTOTOSubjectLabelBO(SubjectLabelDTO subjectLabelDTO);

    List<SubjectLabelDTO> convertBOTOLabelDTOList(List<SubjectLabelBO> subjectLabelBOList);
}
