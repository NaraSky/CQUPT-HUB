package com.lb.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubjectOptionBO implements Serializable {

    private static final long serialVersionUID = -1053561139019179335L;

    /**
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 答案选项
     */
    private List<SubjectAnswerBO> optionList;

}

