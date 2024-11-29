package com.lb.subject.domain.handler;

import com.lb.subject.common.enums.SubjectInfoTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lb
 * @date 2024/11/29
 * @description 题目类型处理器工厂
 */
@Component
public class SubjectTypeHandlerFactory implements InitializingBean {

    @Resource
    private List<SubjectTypeHandler> subjectTypeHandlers;

    private Map<SubjectInfoTypeEnum, SubjectTypeHandler> subjectTypeHandlerMap = new HashMap<>();

    /**
     * 根据题目类型获取对应的处理器
     *
     * @param type 题目类型
     * @return 处理器
     */
    public SubjectTypeHandler getSubjectTypeHandler(int type) {
        SubjectInfoTypeEnum subjectInfoTypeEnum = SubjectInfoTypeEnum.getByCode(type);
        return subjectTypeHandlerMap.get(subjectInfoTypeEnum);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (SubjectTypeHandler subjectTypeHandler : subjectTypeHandlers) {
            subjectTypeHandlerMap.put(subjectTypeHandler.getHandlerType(), subjectTypeHandler);
        }
    }
}
