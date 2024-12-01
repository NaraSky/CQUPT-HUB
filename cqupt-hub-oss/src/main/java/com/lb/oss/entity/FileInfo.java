package com.lb.oss.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo {
    private String fileName;

    private Boolean directoryFlag;

    private String etag;


}
