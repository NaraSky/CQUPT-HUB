package com.lb.subject.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lb.subject.common.entity.PageResult;
import com.lb.subject.common.enums.IsDeleteFlagEnum;
import com.lb.subject.domain.convert.SubjectInfoConverter;
import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.domain.entity.SubjectOptionBO;
import com.lb.subject.domain.handler.SubjectTypeHandler;
import com.lb.subject.domain.handler.SubjectTypeHandlerFactory;
import com.lb.subject.domain.service.SubjectInfoDomainService;
import com.lb.subject.infra.basic.entity.SubjectInfo;
import com.lb.subject.infra.basic.entity.SubjectLabel;
import com.lb.subject.infra.basic.entity.SubjectMapping;
import com.lb.subject.infra.basic.service.SubjectInfoService;
import com.lb.subject.infra.basic.service.SubjectLabelService;
import com.lb.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    /**
     * 添加学科信息
     *
     * @param subjectInfoBO 学科信息业务对象，包含要添加的学科信息
     *
     * @throws Exception 如果在添加学科信息的过程中出现异常，则抛出该异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.bo:{}", JSON.toJSONString(subjectInfoBO));
        }
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBOTOSubjectInfo(subjectInfoBO);
        subjectInfo.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());

        // 插入数据对象到数据库
        subjectInfoService.insert(subjectInfo);

        // 根据学科类型获取对应的处理器
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getSubjectTypeHandler(subjectInfo.getSubjectType());

        subjectInfoBO.setId(subjectInfo.getId());
        // 调用处理器的添加方法处理业务逻辑
        subjectTypeHandler.add(subjectInfoBO);

        // 获取学科映射信息列表
        List<SubjectMapping> subjectMappings = getSubjectMappings(subjectInfoBO, subjectInfo);

        // 批量插入学科映射信息到数据库
        subjectMappingService.batchInsert(subjectMappings);
    }


    /**
     * 获取学科映射关系列表
     *
     * 根据传入的学科信息业务对象（SubjectInfoBO）和数据对象（SubjectInfo），
     * 为每个学科类别ID和标签ID组合生成一个学科映射对象（SubjectMapping），
     * 并将这些映射对象添加到列表中返回。
     *
     * @param subjectInfoBO 学科信息业务对象，包含学科类别ID列表和标签ID列表
     * @param subjectInfo   学科数据对象，包含学科ID
     * @return 包含所有生成的学科映射对象的列表
     */
    private static List<SubjectMapping> getSubjectMappings(SubjectInfoBO subjectInfoBO, SubjectInfo subjectInfo) {
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> subjectMappings = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId( Long.valueOf(categoryId));
                subjectMapping.setLabelId( Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());
                subjectMappings.add(subjectMapping);
            });
        });
        return subjectMappings;
    }

    /**
     * 根据给定的SubjectInfoBO对象分页查询学科信息
     *
     * @param subjectInfoBO 包含查询条件的SubjectInfoBO对象
     * @return 包含查询结果的PageResult对象，包含分页信息和查询结果列表
     */
    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        // 创建一个PageResult对象
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        // 设置当前页码
        pageResult.setPageNo(subjectInfoBO.getPageNum());
        // 设置每页显示的记录数
        pageResult.setPageSize(subjectInfoBO.getPageSize());
        // 计算当前页的记录起始位置
        int start = (subjectInfoBO.getPageNum() - 1) * subjectInfoBO.getPageSize();

        // 将SubjectInfoBO对象转换为SubjectInfo对象
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBOTOSubjectInfo(subjectInfoBO);

        // 根据条件查询满足条件的记录总数
        int count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId(), subjectInfoBO.getLabelId());

        // 如果没有满足条件的记录，则直接返回空的PageResult对象
        if (count == 0) {
            return pageResult;
        }

        // 根据条件查询满足条件的记录列表
        List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());

        // 将SubjectInfo列表转换为SubjectInfoBO列表
        List<SubjectInfoBO> subjectInfoBOS = SubjectInfoConverter.INSTANCE.convertSubjectInfoListTOBO(subjectInfoList);

        // 设置查询结果列表
        pageResult.setRecords(subjectInfoBOS);
        // 设置总记录数
        pageResult.setTotal(count);

        return pageResult;
    }

    /**
     * 根据学科信息业务对象查询学科信息
     *
     * @param subjectInfoBO 学科信息业务对象，包含学科ID
     * @return 返回包含查询结果的学科信息业务对象
     */
    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        // 根据学科信息业务对象的ID查询学科信息
        SubjectInfo subjectInfo = subjectInfoService.queryById(subjectInfoBO.getId());

        // 根据学科类型获取对应的处理器
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getSubjectTypeHandler(subjectInfo.getSubjectType());

        // 调用处理器的查询方法获取学科选项信息
        SubjectOptionBO optionBO = handler.query(subjectInfo.getId().intValue());

        // 将学科选项信息和学科信息转换为学科信息业务对象
        SubjectInfoBO bo = SubjectInfoConverter.INSTANCE.convertOptionAndInfoTOBO(optionBO, subjectInfo);

        // 创建SubjectMapping对象以查询标签ID
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfo.getId());
        subjectMapping.setIsDeleted(IsDeleteFlagEnum.NORMAL.getCode());

        // 查询与学科关联的标签ID列表
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);

        // 将标签ID列表转换为Long类型的标签ID列表
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());

        // 根据标签ID列表批量查询标签信息
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);

        // 将标签信息转换为标签名称列表
        List<String> labelNameList = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());

        // 将标签名称列表设置到学科信息业务对象中
        bo.setLabelName(labelNameList);

        // 返回包含查询结果的学科信息业务对象
        return bo;
    }





}
