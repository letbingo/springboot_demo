package com.lego.config.security.filter;


import com.lego.config.security.entity.JwtLoginToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录验证拦截器 ,执行顺序在UsernamePasswordAuthenticationFilter拦截器后
 * UsernamePasswordAuthenticationFilter的构造函数指定了拦截路径，即默认拦截Post请求方式的/login请求
 * 可以在配置类中通过formLogin().loginProcessingUrl("/xxx")来指定登陆路径
 * JwtLoginFilter只拦截登陆路径，其余路径则会被JwtTokenFilter拦截
 *
 * @author lego 2019/12/20
 */

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 拦截逻辑
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 根据this.obtainUsername(request)获取request中的用户信息，创建未认证的凭证(etAuthenticated(false))
        JwtLoginToken jwtLoginToken = new JwtLoginToken(this.obtainUsername(request), this.obtainPassword(request));
        // 将认证详情(ip,sessionId)写到凭证，此时凭证中的主体为userDetails
        jwtLoginToken.setDetails(new WebAuthenticationDetails(request));

        // this.getAuthenticationManager()来获取用户认证的管理类
        // 所有的认证请求（比如login）都会通过提交一个token给AuthenticationManager的authenticate()方法来实现
        // 具体校验动作会由AuthenticationManager将请求转发给具体的实现类来做，这里的是JwtAuthenticationProvider
        // 跳转到JwtAuthenticationProvider.authenticate方法中认证
        Authentication authenticatedToken = this.getAuthenticationManager().authenticate(jwtLoginToken);
        return authenticatedToken;
    }
}