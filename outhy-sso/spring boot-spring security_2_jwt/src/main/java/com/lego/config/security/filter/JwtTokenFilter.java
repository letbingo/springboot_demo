package com.lego.config.security.filter;

import com.alibaba.fastjson.JSON;
import com.lego.common.RespCode;
import com.lego.common.RespResult;
import com.lego.pojo.User;
import com.lego.config.security.entity.JwtLoginToken;
import com.lego.config.security.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token有效性验证拦截器
 *
 * @author lego 2019/12/20
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            String token = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                // 未对登陆的进行拦截
                // response.setContentType("application/json;charset=UTF-8");
                // RespResult respResult = new RespResult(RespCode.UnLogin.getCode(), RespCode.UnLogin.getMessage());
                // response.getWriter().write(JSON.toJSONString(respResult));

                // 未登陆的直接放行，让Spring Security去判断权限
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = JwtTokenUtils.parseJwt(token);
            if (JwtTokenUtils.isTokenExpired(claims)) {
                response.setContentType("application/json;charset=UTF-8");
                RespResult respResult = new RespResult(RespCode.TokenFail);
                response.getWriter().write(JSON.toJSONString(respResult));
                return;
            }

            // 如果验证通过，则将token保存在Security上下文中，并进行下一步调用
            User user = JSON.parseObject(claims.get(JwtTokenUtils.USER_INFO, String.class), User.class);
            JwtLoginToken jwtLoginToken = new JwtLoginToken(user, "", user.getAuthorities());
            jwtLoginToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(jwtLoginToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("请求出错");
        }

    }

}