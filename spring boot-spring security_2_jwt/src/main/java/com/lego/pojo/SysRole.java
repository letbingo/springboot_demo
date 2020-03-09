package com.lego.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SysRole implements GrantedAuthority {
    private int id;
    private String role;
    private String name;

    @JSONField(serialize = false)
    @Override
    public String getAuthority() {
        return role;
    }
}
