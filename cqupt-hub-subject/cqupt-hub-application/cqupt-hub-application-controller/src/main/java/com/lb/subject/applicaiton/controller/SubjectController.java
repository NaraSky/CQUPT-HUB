package com.lb.subject.applicaiton.controller;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Preconditions;
import com.lb.subject.applicaiton.converter.SubjectAnswerDTOConverter;
import com.lb.subject.applicaiton.converter.SubjectInfoDTOConverter;
import com.lb.subject.applicaiton.dto.SubjectInfoDTO;
import com.lb.subject.common.entity.PageResult;
import com.lb.subject.common.entity.Result;
import com.lb.subject.domain.entity.SubjectAnswerBO;
import com.lb.subject.domain.entity.SubjectInfoBO;
import com.lb.subject.domain.service.SubjectInfoDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class SubjectController {

    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;

    /**
     * 新增题目
     *
     * @param subjectInfoDTO
     * @return
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }

            Preconditions.checkArgument(!StringUtils.isBlank(subjectInfoDTO.getSubjectName()), "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectDifficult(), "题目难度不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(), "题目类型不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectScore(), "题目分数不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getCategoryIds()), "分类id不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getLabelIds()), "标签id不能为空");

            // 将DTO转换为BO
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDTOTOSubjectBO(subjectInfoDTO);
            // 将DTO中的选项列表转换为BO列表
            List<SubjectAnswerBO> subjectAnswerBOS = SubjectAnswerDTOConverter.INSTANCE.convertDTOTOBOList(subjectInfoDTO.getOptionList());
            // 设置BO中的选项列表
            subjectInfoBO.setOptionList(subjectAnswerBOS);
            // 调用领域服务添加题目
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.success(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增题目失败");
        }
    }

    /**
     * 获取题目分页数据接口
     *
     * @param subjectInfoDTO 包含请求参数的DTO对象
     * @return 包含分页结果的Result对象
     */
    @PostMapping("/getSubjectPage")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPage.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkNotNull(subjectInfoDTO.getCategoryId(), "分类id不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getLabelId(), "标签id不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDTOTOSubjectBO(subjectInfoDTO);
            PageResult<SubjectInfoBO> pageResult = subjectInfoDomainService.getSubjectPage(subjectInfoBO);

            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("SubjectController.getSubjectPage.error:{}", e.getMessage(), e);
            return Result.fail("获取题目分页数据失败");
        }
    }

    @PostMapping("/querySubjectInfo")
    /**
     * 查询题目信息
     *
     * @param subjectInfoDTO 包含查询题目信息所需条件的DTO对象
     * @return 返回包含查询结果的Result对象，其中封装了SubjectInfoDTO类型的题目信息
     */
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.querySubjectInfo.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkNotNull(subjectInfoDTO.getId(), "题目id不能为空");
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDTOTOSubjectBO(subjectInfoDTO);
            SubjectInfoBO boResult = subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
            SubjectInfoDTO dto = SubjectInfoDTOConverter.INSTANCE.convertBOTOSubjectInfoDTO(boResult);
            return Result.success(dto);
        } catch (Exception e) {
            log.error("SubjectController.querySubjectInfo.error", e);
            return Result.fail("查询题目详情失败    ");
        }
    }




}
