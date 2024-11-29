package com.lb.subject.applicaiton.controller;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Preconditions;
import com.lb.subject.applicaiton.converter.SubjectLabelDTOConverter;
import com.lb.subject.applicaiton.dto.SubjectLabelDTO;
import com.lb.subject.common.entity.Result;
import com.lb.subject.domain.convert.SubjectLabelConverter;
import com.lb.subject.domain.entity.SubjectLabelBO;
import com.lb.subject.domain.service.SubjectLabelDomainService;
import com.lb.subject.infra.basic.entity.SubjectLabel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subject/label")
public class SubjectLabelController {
    @Resource
    private SubjectLabelDomainService subjectLabelDomainService;

    /**
     * 新增标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.add.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(subjectLabelDTO.getLabelName()), "标签名称不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOTOSubjectLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.add(subjectLabelBO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.add.error:{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 更新标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.update.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkArgument(subjectLabelDTO.getId() != null, "标签ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOTOSubjectLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.update(subjectLabelBO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.update.error:{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 删除标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.delete.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkArgument(subjectLabelDTO.getId() != null, "标签ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOTOSubjectLabelBO(subjectLabelDTO);
            Boolean result = subjectLabelDomainService.delete(subjectLabelBO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("SubjectLabelController.delete.error:{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 查询标签
     *
     * @param subjectLabelDTO
     * @return
     */
    @PostMapping("/queryLabelByCategoryId")
    public Result<SubjectLabelDTO> queryLabelByCategoryId(@RequestBody SubjectLabelDTO subjectLabelDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectLabelController.queryLabelByCategoryId.dto:{}", JSON.toJSONString(subjectLabelDTO));
            }
            Preconditions.checkArgument(subjectLabelDTO.getCategoryId() != null, "分类ID不能为空");
            SubjectLabelBO subjectLabelBO = SubjectLabelDTOConverter.INSTANCE.convertDTOTOSubjectLabelBO(subjectLabelDTO);
            List<SubjectLabelBO> subjectLabelBOList = subjectLabelDomainService.queryLabelByCategoryId(subjectLabelBO);
            List<SubjectLabelDTO> subjectLabelDTOList = SubjectLabelDTOConverter.INSTANCE.convertBOTOLabelDTOList(subjectLabelBOList);
            return Result.success(subjectLabelDTOList);
        } catch (Exception e) {
            log.error("SubjectLabelController.queryLabelByCategoryId.error:{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }
}