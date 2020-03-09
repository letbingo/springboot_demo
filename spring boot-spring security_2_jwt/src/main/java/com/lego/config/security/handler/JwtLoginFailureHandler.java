package com.lego.config.security.handler;

import com.lego.common.RespResult;
import com.lego.common.RespCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆验证失败处理
 *
 * @author lego 2019/12/21
 */
@Component
public class JwtLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException {
        String message = "登陆失败";
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            message = "账户名或者密码输入错误!";
        } else if (exception instanceof LockedException) {
            message = "账户被锁定，请联系管理员!";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期，请联系管理员!";
        } else if (exception instanceof AccountExpiredException) {
            message = "账户过期，请联系管理员!";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用，请联系管理员!";
        }

        response.setContentType("application/json;charset=UTF-8");
        RespResult respResult = new RespResult(RespCode.LoginError.getCode(), "登录失败：" + message);
        response.getWriter().write(respResult.toString());
    }
}
