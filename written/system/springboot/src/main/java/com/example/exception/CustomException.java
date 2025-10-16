package com.example.exception;

import com.example.common.enums.HttpStatusEnum;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
public class CustomException extends RuntimeException {

    private String code;
    private String message;

    public CustomException() {}

    public CustomException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(HttpStatusEnum httpStatusEnum) {
        this.code = httpStatusEnum.code;
        this.message = httpStatusEnum.message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}