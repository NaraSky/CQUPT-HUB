package com.lb.subject.applicaiton.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectAnswerDTO implements Serializable {

    private static final long serialVersionUID = 6400929712188275324L;

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

