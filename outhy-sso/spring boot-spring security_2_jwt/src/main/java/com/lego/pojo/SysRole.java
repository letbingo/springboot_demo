package com.lego.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SysRole implements GrantedAuthority {
    private int id;
    private String roleCode;
    private String roleName;

    @JSONField(serialize = false)
    @Override
    public String getAuthority() {
        return roleCode;
    }
}
