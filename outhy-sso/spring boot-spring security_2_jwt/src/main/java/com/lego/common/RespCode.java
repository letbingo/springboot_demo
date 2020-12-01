package com.lego.common;

/**
 * 返回信息时的标识码定义
 */
public enum RespCode {
    // 成功
    OperSuccess(200, "操作成功"),
    UnLogin(700, "未登录"),
    TokenFail(701, "Token过期"),
    OperFailure(702, "操作失败"),
    LoginError(703, "验证登录失败"),
    NoRight(704, "没有权限"),
    BusinessError(705, "业务异常"),
    BlankParamsError(706, "空参数"),
    ParamsFormatError(707, "参数格式错误"),
    SystemError(708, "系统错误");

    private int code;
    private String message;

    private RespCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
