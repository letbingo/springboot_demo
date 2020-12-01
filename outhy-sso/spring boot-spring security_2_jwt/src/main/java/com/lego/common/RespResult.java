package com.lego.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class RespResult {
    private int code;
    private String message;
    private Object data;
    @JSONField(serialize = false)
    private String dataKey;

    /**
     * 设置返回值的key
     */
    public RespResult(int code, String message, Object data, String dataKey) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.dataKey = dataKey;
    }

    /**
     * 不设置返回值的key
     */
    public RespResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 不设置返回值
     */
    public RespResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 使用RespCode中设置的返回信息
     */
    public RespResult(RespCode respCode) {
        this.code = respCode.getCode();
        this.message = respCode.getMessage();
    }

    public RespResult() {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", message);
        jsonObject.put(null == dataKey ? "data" : dataKey, data);
        return jsonObject;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this.toJson(), SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
    }

}
