package com.lb.subject.applicaiton.controller;

import com.alibaba.fastjson2.JSON;
import com.lb.subject.applicaiton.converter.SubjectCategoryDTOConverter;
import com.lb.subject.applicaiton.converter.SubjectLabelDTOConverter;
import com.lb.subject.applicaiton.dto.SubjectCategoryDTO;
import com.lb.subject.applicaiton.dto.SubjectLabelDTO;
import com.lb.subject.common.entity.Result;
import com.lb.subject.domain.entity.SubjectCategoryBO;
import com.lb.subject.domain.service.SubjectCategoryDomainService;
import com.lb.subject.infra.basic.entity.SubjectCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Preconditions;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/subject/category")
@Slf4j
public class SubjectCategoryController {
    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * 新增学科分类
     *
     * @param subjectCategoryDTO 学科分类
     * @return Result<Boolean>
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.add.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "学科类型不能为空");
            Preconditions.checkArgument(!StringUtils.isEmpty(subjectCategoryDTO.getCategoryName()), "学科名称不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父级学科分类不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertSubjectDTOToBO(subjectCategoryDTO);
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.success();
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.exception:{}, trace:{}", e.getMessage(), e.getStackTrace());
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 查询一级大类
     *
     * @return Result<List < SubjectCategoryDTO>>
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertSubjectDTOToBO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertBOToCategoryDTOList(subjectCategoryBOList);
            return Result.success(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.exception:{}, trace:{}", e.getMessage(), e.getStackTrace());
            return Result.fail("查询一级大类失败");
        }
    }

    /**
     * 查询二级大类
     *
     * @param subjectCategoryDTO 学科分类
     * @return Result<List < SubjectCategoryDTO>>
     */
    @PostMapping("queryCategoryByPrimary")
    public Result<List<SubjectCategoryDTO>> queryCategoryByPrimary(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryByPrimary.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "父级学科分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertSubjectDTOToBO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertBOToCategoryDTOList(subjectCategoryBOList);
            return Result.success(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryCategoryByPrimary.exception:{}, trace:{}", e.getMessage(), e.getStackTrace());
            return Result.fail("查询二级大类失败");
        }
    }

    /**
     * 更新学科分类
     *
     * @param subjectCategoryDTO 学科分类
     * @return Result<Boolean>
     */
    @PostMapping("update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.update.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertSubjectDTOToBO(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.update.exception:{}, trace:{}", e.getMessage(), e.getStackTrace());
            return Result.fail("更新学科分类失败");
        }
    }

    /**
     * 删除学科分类
     *
     * @param subjectCategoryDTO 学科分类
     * @return Result<Boolean>
     */
    @PostMapping("delete")
    public Result<Boolean> delete(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.delete.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertSubjectDTOToBO(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.delete.exception:{}, trace:{}", e.getMessage(), e.getStackTrace());
            return Result.fail("删除学科分类失败");
        }
    }

    @PostMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> queryCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryAndLabel.dto:{}"
                        , JSON.toJSONString(subjectCategoryDTO));
            }
            Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertSubjectDTOToBO(subjectCategoryDTO);
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategoryAndLabel(subjectCategoryBO);
            List<SubjectCategoryDTO> dtoList = new LinkedList<>();
            subjectCategoryBOList.forEach(bo -> {
                SubjectCategoryDTO dto = SubjectCategoryDTOConverter.INSTANCE.convertBoToCategoryDTO(bo);
                List<SubjectLabelDTO> labelDTOList = SubjectLabelDTOConverter.INSTANCE.convertBOTOLabelDTOList(bo.getLabelBOList());
                dto.setLabelDTOList(labelDTOList);
                dtoList.add(dto);
            });
            return Result.success(dtoList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }

}

