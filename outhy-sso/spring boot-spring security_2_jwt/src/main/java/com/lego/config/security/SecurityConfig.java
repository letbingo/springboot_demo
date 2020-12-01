package com.lego.config.security;

import com.lego.config.security.filter.JwtLoginFilter;
import com.lego.config.security.filter.JwtTokenFilter;
import com.lego.config.security.jwt.JwtAuthenticationProvider;
import com.lego.service.impl.UserDetailsServiceImpl;
import com.lego.config.security.handler.JwtAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security的配置类
 *
 * @author lego 2019/12/13
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    // @Autowired
    // private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * url拦截规则
     *
     * @param httpSecurity http安全对象
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 自定义登陆拦截器
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        jwtLoginFilter.setAuthenticationManager(authenticationManagerBean());
        jwtLoginFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        jwtLoginFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();

        // 使用自定义验证实现器
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(userDetailsService, passwordEncoder());

        httpSecurity.authenticationProvider(jwtAuthenticationProvider)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/prod/apply").permitAll()
                .antMatchers("/druid/**", "/sys/areas").permitAll()
                .antMatchers("/user/unlogin").permitAll()
                .antMatchers("/oauth/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/user/unlogin");

        // 这两行将定义的JWT方法加入SpringSecurity的处理流程中
        httpSecurity.sessionManagement()
                // 禁用session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable()
                // 添加拦截器
                .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenFilter, JwtLoginFilter.class);

        // 权限处理信息
        httpSecurity.exceptionHandling()
                // 用来解决认证过的用户访问无权限资源时的异常
                .accessDeniedHandler(accessDeniedHandler);
        // 用来解决匿名用户访问无权限资源时的异常
        // .authenticationEntryPoint(jwtAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /**
     * 密码加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
