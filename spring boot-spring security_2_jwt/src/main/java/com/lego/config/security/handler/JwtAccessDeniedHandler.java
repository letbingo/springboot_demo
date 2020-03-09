package com.lego.config.security.handler;

import com.lego.common.RespCode;
import com.lego.common.RespResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足异常处理
 *
 * @author lego 2019/12/21
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        RespResult respResult = new RespResult(RespCode.NoRight.getCode(),
                accessDeniedException.getMessage());
        response.getWriter().write(respResult.toString());
    }
}