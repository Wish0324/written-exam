package com.example.common.enums;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
public enum HttpStatusEnum {

    SUCCESS("200", "请求成功"),
    PARAM_ERROR("400", "参数错误"),
    TOKEN_INVALID_ERROR("401", "TOKEN无效"),
    TOKEN_CHECK_ERROR("401", "TOKEN验证失败"),
    PARAM_LOST_ERROR("4001", "参数丢失"),
    SYSTEM_ERROR("500", "系统错误"),
    USER_EXIST_ERROR("5001", "账户已存在"),
    USER_NOT_LOGIN("5002", "账户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "账户不存在"),
    PARAM_PASSWORD_ERROR("5005", "密码错误");

    public String code;
    public String message;

    HttpStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}