package com.lego.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.lego.common.RespResult;
import com.lego.pojo.User;
import com.lego.common.RespCode;
import com.lego.config.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆验证成功处理
 *
 * @author lego 2019/12/21
 */
@Component
public class JwtLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        User user = (User) authentication.getPrincipal();
        // 隐藏密码
        user.setPassword(null);
        String json = JSON.toJSONString(user);
        String jwtToken = JwtTokenUtils.createJwtToken(json);

        // 备用：将token值返回到response的header中
        // response.setHeader(JwtTokenUtils.TOKEN_HEADER, jwtToken);

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("Authorization", jwtToken);

        // 成功后返回token值
        RespResult respResult = new RespResult(RespCode.OperSuccess.getCode(),
                "登录成功！", jwtToken, JwtTokenUtils.TOKEN_HEADER);
        response.getWriter().write(respResult.toString());
    }
}