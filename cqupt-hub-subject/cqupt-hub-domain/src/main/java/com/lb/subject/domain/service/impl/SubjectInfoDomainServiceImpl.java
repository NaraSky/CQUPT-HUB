package com.lb.subject.domain.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lb.subject.domain.convert.SubjectInfoConverter;
import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.domain.handler.SubjectTypeHandler;
import com.lb.subject.domain.handler.SubjectTypeHandlerFactory;
import com.lb.subject.domain.service.SubjectInfoDomainService;
import com.lb.subject.infra.basic.entity.SubjectInfo;
import com.lb.subject.infra.basic.entity.SubjectMapping;
import com.lb.subject.infra.basic.service.SubjectInfoService;
import com.lb.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {


    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    /**
     * 添加学科信息
     *
     * @param subjectInfoBO 学科信息业务对象，包含要添加的学科信息
     */
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.bo:{}", JSON.toJSONString(subjectInfoBO));
        }
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBOTOSubjectInfo(subjectInfoBO);

        // 插入数据对象到数据库
        subjectInfoService.insert(subjectInfo);

        // 根据学科类型获取对应的处理器
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getSubjectTypeHandler(subjectInfo.getSubjectType());

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
                subjectMappings.add(subjectMapping);
            });
        });
        return subjectMappings;
    }

}
