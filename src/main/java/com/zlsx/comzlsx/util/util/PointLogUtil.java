package com.zlsx.comzlsx.util.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * 后台埋点日志
 *
 * @author yp
 */
@Slf4j
public class PointLogUtil {
    private final static String LOG_SE = " ";
    private final static String SUPER_BASE_NAME = "base";

    public static void toPoint(String code, Object... value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        String v = "";
        for (Object o : value) {
            v += PointLogUtil.toPoint(o);
        }
        jsonObject.put("value", v.isEmpty() ? value : v.substring(0, v.length() - 1));
        log.info("后端埋点日志==" + jsonObject.toJSONString());
    }

    private static String toPoint(Object o) {
        String value = "";
        if (o == null) {
            return value;
        }
        String className = o.getClass().getSuperclass().getName();
        if (StringUtils.equals(Object.class.getName(), className)) {
            return o.toString() + LOG_SE;
        }
        if (!className.toLowerCase().contains(SUPER_BASE_NAME)) {
            return o.toString() + LOG_SE;
        }
        value += toPointClass(o, o.getClass().getSuperclass());
        value += toPointClass(o, o.getClass());
        return value;
    }

    private static String toPointClass(Object o, Class c) {
        String value = "";
        Field[] declaredFields = c.getDeclaredFields();
        Object v;
        for (Field declaredField : declaredFields) {
            boolean flag = declaredField.isAccessible();
            declaredField.setAccessible(true);
            String fileName = declaredField.getName();
            try {
                v = declaredField.get(o);
                value += fileName + LOG_SE + v + LOG_SE;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                declaredField.setAccessible(flag);
            }
        }
        return value;
    }


    public static void main(String[] args) {
        System.out.println(Object.class.getName());
    }

}
