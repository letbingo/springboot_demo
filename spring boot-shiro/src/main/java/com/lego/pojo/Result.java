package com.lego.pojo;

import com.alibaba.fastjson.JSONObject;

public class Result {
    private String Message;
    private boolean Success = false;
    private Object Content = null;
    private String code;

    public Object getContent() {
        return Content;
    }

    public void setContent(Object content) {
        Content = content;
    }

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", Success);
        jsonObject.put("message", Message);
        jsonObject.put("info", Content);
        jsonObject.put("code", code);
        return jsonObject.toJSONString();
    }

}
