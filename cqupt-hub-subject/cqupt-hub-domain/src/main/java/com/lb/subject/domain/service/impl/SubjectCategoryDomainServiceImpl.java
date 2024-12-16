package com.lb.subject.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lb.subject.common.enums.IsDeleteFlagEnum;
import com.lb.subject.domain.convert.SubjectCategoryConverter;
import com.lb.subject.domain.entity.SubjectCategoryBO;
import com.lb.subject.domain.entity.SubjectLabelBO;
import com.lb.subject.domain.service.SubjectCategoryDomainService;
import com.lb.subject.infra.basic.entity.SubjectCategory;
import com.lb.subject.infra.basic.entity.SubjectLabel;
import com.lb.subject.infra.basic.entity.SubjectMapping;
import com.lb.subject.infra.basic.service.SubjectCategoryService;
import com.lb.subject.infra.basic.service.SubjectLabelService;
import com.lb.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    /**
     * 新增学科分类
     *
     * @param subjectCategoryBO 学科分类BO
     */
    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        if (log.isDebugEnabled()) {
            log.info("SubjectCategoryController.add.bo:{}", JSON.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBOToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
        subjectCategoryService.insert(subjectCategory);
    }

    /**
     * 查询学科分类
     *
     * @param subjectCategoryBO 学科分类BO
     * @return 学科分类BO列表
     */
    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBOToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryConverter.INSTANCE.convertCategoryTOBOList(subjectCategoryList);
        if (log.isDebugEnabled()) {
            log.info("SubjectCategoryController.queryCategory.bo:{}", JSON.toJSONString(subjectCategoryBOList));
        }
        subjectCategoryBOList.forEach(subjectCategoryBO1 -> {
            Integer subjectCount = subjectCategoryService.querySubjectCount(subjectCategoryBO1.getId());
            subjectCategoryBO1.setCount(subjectCount);
        });
        return subjectCategoryBOList;
    }


    /**
     * 更新学科分类
     *
     * @param subjectCategoryBO 学科分类BO
     * @return 是否更新成功
     */
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE
                .convertBOToCategory(subjectCategoryBO);
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }

    /**
     * 删除学科分类
     * @param subjectCategoryBO 学科分类BO
     * @return 是否删除成功
     */
    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE
                .convertBOToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }

    /**
     * 查询指定大类下的所有分类及其对应的标签信息
     *
     * @param subjectCategoryBO 查询条件，包含大类ID
     * @return 包含分类及其对应标签信息的业务对象列表
     */
    @Override
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        //查询当前大类下所有分类
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setParentId(subjectCategoryBO.getId());
        subjectCategory.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}",
                    JSON.toJSONString(subjectCategoryList));
        }

        // 将查询到的分类转换为业务对象列表
        List<SubjectCategoryBO> categoryBOList = SubjectCategoryConverter.INSTANCE.convertCategoryTOBOList(subjectCategoryList);

        //一次获取标签信息
        categoryBOList.forEach(category -> {
            SubjectMapping subjectMapping = new SubjectMapping();
            subjectMapping.setCategoryId(category.getId());
            List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
            if (CollectionUtils.isEmpty(mappingList)) {
                return;
            }

            // 获取标签ID列表
            List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());

            // 根据标签ID列表批量查询标签信息
            List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);

            // 将查询到的标签信息转换为业务对象列表
            List<SubjectLabelBO> labelBOList = new LinkedList<>();
            labelList.forEach(label -> {
                SubjectLabelBO subjectLabelBO = new SubjectLabelBO();
                subjectLabelBO.setId(label.getId());
                subjectLabelBO.setLabelName(label.getLabelName());
                subjectLabelBO.setCategoryId(label.getCategoryId());
                subjectLabelBO.setSortNum(label.getSortNum());
                labelBOList.add(subjectLabelBO);
            });

            // 将标签业务对象列表设置到分类业务对象中
            category.setLabelBOList(labelBOList);
        });

        return categoryBOList;
    }


}
