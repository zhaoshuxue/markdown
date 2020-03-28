package com.zsx.md.vo;

import lombok.Data;

@Data
public class ResultData<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;

    public static ResultData build(boolean success, String message, Object data) {
        ResultData<Object> resultData = new ResultData<>();
        resultData.setSuccess(success);
        resultData.setMessage(message);
        resultData.setData(data);
        return resultData;
    }

}
