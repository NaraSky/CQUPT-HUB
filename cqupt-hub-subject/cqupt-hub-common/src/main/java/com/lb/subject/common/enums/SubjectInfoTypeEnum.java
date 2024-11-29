package com.lb.subject.common.enums;

import lombok.Getter;

@Getter
public enum SubjectInfoTypeEnum {

    RADIO(1,"单选"),
    MULTIPLE(2,"多选"),
    JUDGE(3,"判断"),
    BRIEF(4,"简答"),
    ;

    public int code;
    public String desc;

    SubjectInfoTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SubjectInfoTypeEnum getByCode(int code){
        for (SubjectInfoTypeEnum type : values()) {
            if(type.code == code){
                return type;
            }
        }
        throw new RuntimeException("不存在该题型");
    }
}
