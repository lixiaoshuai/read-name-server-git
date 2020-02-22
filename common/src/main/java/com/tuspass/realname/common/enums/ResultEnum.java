package com.tuspass.realname.common.enums;

/**
 * result枚举
 *
 *
 */
public enum ResultEnum {
    OK("0000", "success"),
    ERROR("9999", "error"),
    TOKEN_INVALID("0001", "token无效"),
    PARAMETER_ERROR("0002", "请求参数错误"),
    WX_MINI_PROGRAM_FAILED("0003", "微信小程序接口访问失败"),

    USER_FAILED("0005", "用户异常"),
    SYSTEM_ERROR("FFFF", "系统错误")
    ;

    /**
     * 状态码
     */
    private String result;

    /**
     * 提示消息
     */
    private String msg;

    ResultEnum(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }
}
