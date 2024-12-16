package com.lb.subject.applicaiton.converter;

import com.lb.subject.applicaiton.dto.SubjectCategoryDTO;
import com.lb.subject.domain.entity.SubjectCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryDTOConverter {

    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);

    SubjectCategoryBO convertSubjectDTOToBO(SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategoryDTO> convertBOToCategoryDTOList(List<SubjectCategoryBO> subjectCategoryDTO);

    SubjectCategoryDTO convertBoToCategoryDTO(SubjectCategoryBO subjectCategoryBO);


}
