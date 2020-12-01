package com.lego.config.security.handler;

import com.lego.common.RespCode;
import com.lego.common.RespResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户权限不足处理
 *
 * @author lego 2019/12/21
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        RespResult respResult = new RespResult(RespCode.NoRight.getCode(),
                "权限不足 :" + authException.getMessage());
        response.getWriter().write(respResult.toString());
    }
}