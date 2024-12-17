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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
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

    @Resource
    private ThreadPoolExecutor labelThreadPool;


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
    @SneakyThrows
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        // 查询当前大类下所有分类
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setParentId(subjectCategoryBO.getId());
        subjectCategory.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryCategoryAndLabel.subjectCategoryList:{}",
                    JSON.toJSONString(subjectCategoryList));
        }
        List<SubjectCategoryBO> categoryBOList = SubjectCategoryConverter.INSTANCE.convertCategoryTOBOList(subjectCategoryList);

        // 一次获取标签信息
        List<FutureTask<Map<Long, List<SubjectLabelBO>>>> futureTaskList = new LinkedList<>();
        // 线程池并发调用
        Map<Long, List<SubjectLabelBO>> map = new HashMap<>();
        categoryBOList.forEach(category -> {
            // 创建FutureTask任务，任务内容为调用getLabelBOList方法获取标签信息
            FutureTask<Map<Long, List<SubjectLabelBO>>> futureTask = new FutureTask<>(() -> getLabelBOList(category));
            futureTaskList.add(futureTask);
            // 提交任务到线程池执行
            labelThreadPool.submit(futureTask);
        });

        // 等待所有任务完成并获取结果
        for (FutureTask<Map<Long, List<SubjectLabelBO>>> futureTask : futureTaskList) {
            Map<Long, List<SubjectLabelBO>> resultMap = futureTask.get();
            if (CollectionUtils.isEmpty(resultMap)) {
                continue;
            }
            map.putAll(resultMap);
        }

        // 为每个分类设置对应的标签列表
        categoryBOList.forEach(categoryBO -> {
            categoryBO.setLabelBOList(map.get(categoryBO.getId()));
        });

        return categoryBOList;
    }

    /**
     * 根据学科分类获取学科标签列表
     *
     * @param category 学科分类对象
     * @return 学科标签列表的映射，键为学科分类ID，值为学科标签列表
     */
    private Map<Long, List<SubjectLabelBO>> getLabelBOList(SubjectCategoryBO category) {
        // 创建一个HashMap来存储学科标签列表的映射
        Map<Long, List<SubjectLabelBO>> labelMap = new HashMap<>();

        // 创建一个SubjectMapping对象
        SubjectMapping subjectMapping = new SubjectMapping();
        // 设置学科分类ID
        subjectMapping.setCategoryId(category.getId());

        // 查询与学科分类关联的标签ID列表
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);

        // 如果标签ID列表为空，则返回null
        if (CollectionUtils.isEmpty(mappingList)) {
            return null;
        }

        // 将标签ID列表转换为Long类型的列表
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());

        // 根据标签ID列表批量查询标签信息
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);

        // 创建一个LinkedList来存储转换后的标签业务对象列表
        List<SubjectLabelBO> labelBOList = new LinkedList<>();

        // 遍历标签列表，将每个标签转换为标签业务对象并添加到列表中
        labelList.forEach(label -> {
            SubjectLabelBO subjectLabelBO = new SubjectLabelBO();
            subjectLabelBO.setId(label.getId());
            subjectLabelBO.setLabelName(label.getLabelName());
            subjectLabelBO.setCategoryId(label.getCategoryId());
            subjectLabelBO.setSortNum(label.getSortNum());
            labelBOList.add(subjectLabelBO);
        });

        // 将学科分类ID和对应的标签业务对象列表添加到映射中
        labelMap.put(category.getId(), labelBOList);

        // 返回映射
        return labelMap;
    }



}
