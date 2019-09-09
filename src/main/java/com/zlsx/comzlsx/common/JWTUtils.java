package com.zlsx.comzlsx.common;

import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.ResultCode;
import com.zlsx.comzlsx.dto.enums.ExpireTimeEnum;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : houxm
 * @date : 2018/10/11 16:46
 * @description :
 */
@Component
@Slf4j
public class JWTUtils {
    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.exp}")
    private Integer exp;
    @Resource
    private HttpServletRequest request;

    public String createKey(Map map, ExpireTimeEnum expireTimeEnum) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key keySpec = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        JwtBuilder builder = Jwts.builder();
        builder.setClaims(map);
        builder.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        if (ExpireTimeEnum.HOUR.equals(expireTimeEnum)) {
            builder.setExpiration(DateTime.now().plusHours(exp).toDate());
        } else {
            builder.setExpiration(DateTime.now().plusYears(exp).toDate());
        }
        builder.setIssuedAt(new Date());
        builder.setNotBefore(new Date());
        builder.signWith(keySpec);
        String jws = builder.compact();
        return jws;
    }

    public String createKey(String oldUserId, String nickName, String avatar, String mobile, String openId, ExpireTimeEnum expireTimeEnum) {
        Map map = new HashMap();
        map.put("userId", oldUserId);
        map.put("nickName", nickName);
        map.put("avatar", avatar);
        map.put("mobile", mobile);
        map.put("openId", openId);
        return createKey(map, expireTimeEnum);
    }

    public Claims parseBody(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims;
    }

    public String getUserIdWithNoExce() throws ForeseenException {
        String token = request.getHeader("TOKEN");
        if (StringUtils.isBlank(token)) {
            return "";
        }
        Claims claims = null;
        try {
            claims = parseBody(token);
            return claims.get("userId").toString();
        } catch (Exception e) {
            throw new ForeseenException(ResultCode.AUTH_FAILED, "解析token失败");
        }
    }

    public Integer getUserId() throws ForeseenException {
        try {
            String token = request.getHeader("TOKEN");
            if (StringUtils.isBlank(token)) {
                throw new ForeseenException(ResultCode.AUTH_FAILED, "未登录");
            }
            Claims claims = parseBody(token);
            return Integer.valueOf(claims.get("userId").toString());
        } catch (Exception ex) {
            throw new ForeseenException(ResultCode.AUTH_FAILED, "解析token失败");
        }
    }

}
