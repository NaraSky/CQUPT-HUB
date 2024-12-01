package com.lb.subject.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lb.subject.common.enums.CategoryTypeEnum;
import com.lb.subject.common.enums.IsDeleteFlagEnum;
import com.lb.subject.domain.convert.SubjectLabelConverter;
import com.lb.subject.domain.entity.SubjectLabelBO;
import com.lb.subject.domain.service.SubjectLabelDomainService;
import com.lb.subject.infra.basic.entity.SubjectCategory;
import com.lb.subject.infra.basic.entity.SubjectLabel;
import com.lb.subject.infra.basic.entity.SubjectMapping;
import com.lb.subject.infra.basic.service.SubjectCategoryService;
import com.lb.subject.infra.basic.service.SubjectLabelService;
import com.lb.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    /**
     * 新增标签
     *
     * @param subjectLabelBO SubjectLabelBO
     * @return boolean
     */
    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.add.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBOTOSubjectLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
        int insert = subjectLabelService.insert(subjectLabel);
        return insert > 0;
    }

    /**
     * 更新标签
     *
     * @param subjectLabelBO SubjectLabelBO
     * @return boolean
     */
    @Override
    public Boolean update(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.update.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBOTOSubjectLabel(subjectLabelBO);
        int update = subjectLabelService.update(subjectLabel);
        return update > 0;
    }

    /**
     * 删除标签
     * @param subjectLabelBO SubjectLabelBO
     * @return boolean
     */
    @Override
    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.delete.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBOTOSubjectLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeleteFlagEnum.DELETE.getCode());
        int update = subjectLabelService.update(subjectLabel);
        return update > 0;
    }

    /**
     * 根据分类ID查询学科标签列表
     *
     * @param subjectLabelBO 包含分类ID的学科标签对象
     * @return 符合条件的学科标签列表，如果未找到匹配的标签，则返回空列表
     */
    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.queryLabelByCategoryId.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        //如果当前分类是1级分类，则查询所有标签
        SubjectCategory subjectCategory = subjectCategoryService.queryById(subjectLabelBO.getCategoryId());
        if(subjectCategory.getCategoryType() == CategoryTypeEnum.PRIMARY.getCode()){
            SubjectLabel subjectLabel = new SubjectLabel();
            subjectLabel.setCategoryId(subjectLabelBO.getCategoryId());
            List<SubjectLabel> labelList = subjectLabelService.queryByCondition(subjectLabel);
            List<SubjectLabelBO> labelResultList = SubjectLabelConverter.INSTANCE.convertSubjectLabelListTOBO(labelList);
            return labelResultList;
        }

        // 根据分类ID查询学科标签映射关系
        Long categoryId = subjectLabelBO.getCategoryId();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        subjectMapping.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());

        List<SubjectMapping> subjectMappingList = subjectMappingService.queryLabelId(subjectMapping);

        // 如果没有找到匹配的映射关系，则返回空列表
        if (subjectMappingList == null || subjectMappingList.isEmpty()) {
            return Collections.emptyList();
        }

        // 根据映射关系中的标签ID查询标签信息
        List<Long> labelIdList = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> subjectLabelList = subjectLabelService.batchQueryById(labelIdList);

        // 将标签信息转换为BO对象并返回
        List<SubjectLabelBO> subjectLabelBOList = new LinkedList<>();
        subjectLabelList.forEach(label -> {
            SubjectLabelBO labelBO = new SubjectLabelBO();
            labelBO.setId(label.getId());
            labelBO.setLabelName(label.getLabelName());
            labelBO.setSortNum(label.getSortNum());
            labelBO.setCategoryId(categoryId);
            subjectLabelBOList.add(labelBO);
        });

        return subjectLabelBOList;
    }
}
