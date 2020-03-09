package com.lego.config.security;

import com.lego.mapper.RolePermissionMapper;
import com.lego.pojo.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Spring Security配置项
 * 用来储存请求资源(url)与角色的对应关系
 *
 * @author lego 2019/12/12
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 每一个资源所需要的角色（决策器会用到）
     */
    private static HashMap<String, Collection<ConfigAttribute>> requestMap = null;

    /**
     * 当接收到一个http请求时, filterSecurityInterceptor会调用此方法，返回请求的资源需要的角色
     *
     * @param object 包含url信息的HttpServletRequest实例
     * @return 返回请求该url所需要的所有权限集合（permission表中指定的）
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        Iterator<String> it = requestMap.keySet().iterator();
        for (; it.hasNext(); ) {
            String url = it.next();
            if (new AntPathRequestMatcher(url).matches(request)) {
                return requestMap.get(url);
            }
        }
        return null;
    }

    /**
     * 把所有请求与权限的对应关系也要在这个方法里初始化, 保存在一个属性变量里
     * <p>Spring容器启动时自动调用</p>
     *
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        loadResourceDefine();
        return null;
    }

    /**
     * 指示该类是否能够为指定的方法调用或Web请求提供ConfigAttributes
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 初始化permission表中的所有资源（url）对应的角色（role）
     */
    public void loadResourceDefine() {
        requestMap = new HashMap<>(16);
        // 查找所有角色和path的对应关系
        List<RolePermission> lstRolePermissions = rolePermissionMapper.getRolePermission();
        for (RolePermission rolePermission : lstRolePermissions) {
            // 判断某个资源可以被哪些角色访问
            String url = rolePermission.getUrl();
            String role = rolePermission.getRole();
            ConfigAttribute configAttribute = new SecurityConfig(role);
            if (null != url) {
                if (requestMap.containsKey(url)) {
                    requestMap.get(url).add(configAttribute);
                } else {
                    List<ConfigAttribute> lstConfigAttributes = new ArrayList<>();
                    lstConfigAttributes.add(configAttribute);
                    requestMap.put(url, lstConfigAttributes);
                }
            }
        }
    }
}
