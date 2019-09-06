package com.zlsx.comzlsx.common;


import com.zlsx.comzlsx.util.common.Result;
import com.zlsx.comzlsx.util.common.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : houxm
 * @date : 2018/10/15 14:25
 * @description :
 */
@Aspect
@Component
public class SysTokenAspect {
    @Resource
    private JWTUtils jwtUtils;

    @Pointcut("@annotation(com.zlsx.comzlsx.util.annotation.SysTokenIntercept)")
    public void tokenIntercept() {

    }

    @Around(value = "tokenIntercept()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("X-Token");
        //String jToken = null;
        //Map<String, String[]> map = request.getParameterMap();
//        if (map.get("token") == null && StringUtils.isEmpty(token)) {
//            return Result.error(ResultCode.AUTH_FAILED, "令牌不能为空");
//        } else if (map.get("token") == null && StringUtils.isNotEmpty(token)) {
//            jToken = token;
//        } else {
//            jToken = map.get("token")[0];
//        }
        if (StringUtils.isEmpty(token)) {
            return Result.error(ResultCode.AUTH_FAILED, "令牌不能为空");
        }
        try {
            jwtUtils.parseBody(token);
        } catch (ExpiredJwtException e) {
            return Result.error(ResultCode.AUTH_FAILED, "身份已过期");
        } catch (Exception e) {
            return Result.error(ResultCode.AUTH_FAILED, "无效的身份");
        }
        return pjp.proceed();
    }
}
