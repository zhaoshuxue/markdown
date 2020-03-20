package com.zsx.md.vo;

import lombok.Data;

@Data
public class JsonData<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;

}
