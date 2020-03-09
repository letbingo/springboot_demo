package com.lego.config.security.jwt;

import com.lego.config.security.entity.JwtLoginToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用户角色校验具体实现
 * 在配置类中指定了通过authenticationProvider方法配置了校验类
 *
 * @author lego 2019/12/20
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public JwtAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 鉴权具体逻辑
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        // 转换authentication 认证时会先调用support方法,受支持才会调用,所以能强转
        JwtLoginToken jwtLoginToken = (JwtLoginToken) authentication;

        defaultCheck(userDetails);
        additionalAuthenticationChecks(userDetails, jwtLoginToken);

        // 构造已认证的authentication
        JwtLoginToken authenticatedToken = new JwtLoginToken(userDetails, jwtLoginToken.getCredentials(), userDetails.getAuthorities());
        authenticatedToken.setDetails(jwtLoginToken.getDetails());
        return authenticatedToken;
    }

    /**
     * 是否支持当前类
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtLoginToken.class
                .isAssignableFrom(authentication));
    }

    /**
     * 一些默认信息的检查
     *
     * @param user
     */
    private void defaultCheck(UserDetails user) {
        // 只要有一项不符合即认为是失败
        if (null == user) {
            throw new BadCredentialsException("Bad credentials");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        } else if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }
    }

    /**
     * 用户名密码校验具体逻辑
     *
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    private void additionalAuthenticationChecks(UserDetails userDetails, JwtLoginToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        // 将密码加密后与数据库中的对比
        String presentedPassword = authentication.getCredentials().toString();
        // matches()用于对比加密前和加密后的密码
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}