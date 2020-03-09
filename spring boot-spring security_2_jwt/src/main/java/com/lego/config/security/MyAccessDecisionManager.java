package com.lego.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * Spring Security配置项
 * AccessDecisionManager是由AbstractSecurityInterceptor调用，它负责鉴定用户是否有访问对应资源（方法或URL）的权限
 *
 * @author lego 2019/12/12
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     * <p>只有在数据库中有该url所需permission时才会调用该方法</p>
     *
     * @param authentication   当前的用户信息，包括拥有的权限。这里的权限来自登录时UserDetailsService中设置的authorities
     * @param object           包含request等web资源的FilterInvocation对象
     * @param configAttributes 本次访问所需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || 0 >= configAttributes.size()) {
            return;
        } else {
            String strNeedRole;
            Iterator<ConfigAttribute> it = configAttributes.iterator();
            for (; it.hasNext(); ) {
                strNeedRole = it.next().getAttribute();
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    if (strNeedRole.trim().equals(ga.getAuthority().trim())) {
                        return;
                    }
                }
            }
            throw new AccessDeniedException("无访问权限");
        }
    }

    /**
     * 表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
     *
     * @param configAttribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * 表示当前AccessDecisionManager实现是否能够为指定的受保护资源（方法调用或Web请求）提供访问控制决策
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
