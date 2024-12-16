package com.lb.subject.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lb.subject.common.enums.IsDeleteFlagEnum;
import com.lb.subject.domain.convert.SubjectCategoryConverter;
import com.lb.subject.domain.entity.SubjectCategoryBO;
import com.lb.subject.domain.service.SubjectCategoryDomainService;
import com.lb.subject.infra.basic.entity.SubjectCategory;
import com.lb.subject.infra.basic.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

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

}
