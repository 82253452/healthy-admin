package com.zlsx.comzlsx.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * @author houxm
 * @version 1.01 2018/4/16 11:40
 * @description web请求日志拦截器
 */
@Aspect
@Component
public class WebLogAspect {
    private Logger logger = Logger.getLogger("restapi全局日志");

    @Pointcut("execution(public * com.zlsx.restapi.controller..*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("URL:" + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Enumeration<String> headNames = request.getHeaderNames();
        if (headNames != null) {
            while (headNames.hasMoreElements()) {
                String headName = headNames.nextElement();
                logger.info("HTTP_HEADER : " + headName + " | " + request.getHeader(headName));
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (Object x : joinPoint.getArgs()) {
                if (x.getClass().getName().equals("org.apache.catalina.connector.ResponseFacade")) {
                    return;
                }
                logger.info("参数 : " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(x));
            }
        } catch (Exception e) {
            try {
                logger.info("参数 : 参数序列化异常。错误：");
                logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(e));
            } catch (Exception ex) {
                logger.info("参数 : 参数序列化异常。序列化Ex报错");
            }
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("返回内容：" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ret));
        } catch (Exception ex) {
            logger.info("返回内容：对返回值序列化时发生异常。");
        }
    }
}
