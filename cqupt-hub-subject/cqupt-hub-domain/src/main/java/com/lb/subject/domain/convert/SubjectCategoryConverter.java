package com.lb.subject.domain.convert;

import com.lb.subject.domain.entity.SubjectCategoryBO;
import com.lb.subject.infra.basic.entity.SubjectCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectCategoryConverter {

    SubjectCategoryConverter INSTANCE = Mappers.getMapper(SubjectCategoryConverter.class);

    SubjectCategory convertBOToCategory(SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> convertCategoryTOBOList(List<SubjectCategory> categoryList);

}
