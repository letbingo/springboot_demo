package com.lego.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author lego 2019/12/20
 */
@Component
public class JwtTokenUtils {
    /**
     * 过期时间
     */
    public static final long DEFAULT_TOKEN_TIME_MS = 60 * 60 * 1000;
    public static final long REMEMBERME_TOKEN_TIME_MS = 60 * 1000 * 60 * 24;
    /**
     * 签名秘钥
     */
    public static final String SECRET = "token";
    /**
     * 该JWT的签发者
     */
    private static final String ISS = "";
    /**
     * 在浏览器中的key，在前端调用后端接口时，要带上此请求头
     */
    public static final String TOKEN_HEADER = "Authorization";

    public static final String USER_INFO = "UserDetails";

    /**
     * 生成Token
     *
     * @param id 一般传入username
     * @return
     */
    public static String createJwtToken(String id) {
        String issuer = "GYB";
        String subject = "";
        return createJwtToken(id, issuer, subject, DEFAULT_TOKEN_TIME_MS);
    }

    /**
     * 指定过期时间的Token
     *
     * @param id        一般传入username
     * @param ttlMillis 过期时间
     * @return
     */
    public static String createJwtToken(String id, long ttlMillis) {
        String issuer = "GYB";
        String subject = "";
        return createJwtToken(id, issuer, subject, ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id        编号
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param subject   该JWT所面向的用户，是否使用是可选的；
     * @param ttlMillis 签发时间
     * @return token String
     */
    public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {

        // 签名算法，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(USER_INFO, id);

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * 读取JWT
     *
     * @param jwt 待操作JWT
     * @return
     */
    public static Claims parseJwt(String jwt) {
        // 未签名JWT会抛出异常
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 验证jwt的有效期
     *
     * @param claims
     * @return
     */
    public static Boolean isTokenExpired(Claims claims) {
        return claims == null || claims.getExpiration().before(new Date());
    }
}
