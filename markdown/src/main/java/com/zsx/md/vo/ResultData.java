package com.zsx.md.vo;

import lombok.Data;

@Data
public class ResultData<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;

}
