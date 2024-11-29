package com.lb.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectAnswerBO implements Serializable {

    private static final long serialVersionUID = 8754170086098629282L;

    /**
     * 答案选项标识
     */
    private Integer optionType;

    /**
     * 答案
     */
    private String optionContent;

    /**
     * 是否正确
     */
    private Integer isCorrect;

}

