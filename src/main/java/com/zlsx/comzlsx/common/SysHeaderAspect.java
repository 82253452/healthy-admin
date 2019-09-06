package com.zlsx.comzlsx.common;

import com.zlsx.comzlsx.util.annotation.SysHeaderNotIntercept;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author : houxm
 * @date : 2018/10/15 14:25
 * @description :
 */
@Aspect
@Component
public class SysHeaderAspect {

    @Pointcut("execution(public * com.zlsx.comzlsx.controller..*(..))")
    public void headerIntercept() {

    }

    @Around(value = "headerIntercept()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        SysHeaderNotIntercept intercept = currentMethod.getAnnotation(SysHeaderNotIntercept.class);
        if (intercept != null) {
            return pjp.proceed();
        }
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest();
//        String uuid = request.getHeader("appuuid");
//        String sysType = request.getHeader("systype");
//        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(sysType) || uuid.length() > 128) {
//            return Result.error(ResultCode.PARAMS_ERROR, "H参数不合法");
//        }
//        boolean b = Arrays.stream(SysTypeEnum.values()).anyMatch(sysTypeEnum -> StringUtils.equals(sysType, sysTypeEnum.getCode().toString()));
//        if (!b) {
//            return Result.error(ResultCode.PARAMS_ERROR, "H参数不合法");
//        }
        return pjp.proceed();
    }
}
