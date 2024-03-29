package com.zlsx.comzlsx.util.util;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author : houxm
 * @date : 2018/12/21 16:47
 * @description :
 */
public class EntityUtils {

    //private static JWTUtils jwtUtils = SpringUtils.getBean(JWTUtils.class);


    /**
     * 快速将bean的crtUser、crtHost、crtTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setCreateInfo(T entity) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String hostIp = "";
//        String userId = "";
//        if (request != null) {
//            //hostIp = StringUtils.defaultIfBlank(request.getHeader("userHost"), ClientUtil.getClientIp(request));
//            hostIp = ClientUtil.getClientIp(request);
//            String token = request.getHeader("X-Token");
//            if (StringUtils.isNotEmpty(token)) {
//                try {
//                    userId = jwtUtils.getNameAndUserId(token);
//                } catch (Exception ex) {
//
//                }
//            }
//        }


        // 默认属性
        String[] fields = {"createTime", "deleted"};
        Field field = ReflectionUtils.getAccessibleField(entity, "createTime");
        // 默认值
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{new Date(), false};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    /**
     * 快速将bean的updUser、updHost、updTime附上相关值
     *
     * @param entity 实体bean
     * @author 王浩彬
     */
    public static <T> void setUpdatedInfo(T entity) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String hostIp = "";
//        String userId = "";
//        if (request != null) {
//            //hostIp = StringUtils.defaultIfBlank(request.getHeader("userHost"), ClientUtil.getClientIp(request));
//            hostIp = ClientUtil.getClientIp(request);
//            String token = request.getHeader("X-Token");
//            if (StringUtils.isNotEmpty(token)) {
//                try {
//                    userId = jwtUtils.getNameAndUserId(token);
//                } catch (Exception ex) {
//
//                }
//            }
//        }

        // 默认属性
        String[] fields = {"updateTime"};
        Field field = ReflectionUtils.getAccessibleField(entity, "updateTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{new Date()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    public static <T> void setDelete(T entity, Boolean deleted) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String hostIp = "";
//        String userId = "";
//        if (request != null) {
//            //hostIp = StringUtils.defaultIfBlank(request.getHeader("userHost"), ClientUtil.getClientIp(request));
//            hostIp = ClientUtil.getClientIp(request);
//            String token = request.getHeader("token");
//            if (StringUtils.isEmpty(token)) {
//                try {
//                    userId = jwtUtils.getNameAndUserId(token);
//                } catch (Exception ex) {
//
//                }
//            }
//        }

        // 默认属性
        String[] fields = {"updateTime", "deleted"};
        Field field = ReflectionUtils.getAccessibleField(entity, "updateTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{new Date(), deleted};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    public static <T> void setDelete(T entity) {
        setDelete(entity, true);
    }

    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value  值数组
     * @author 王浩彬
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /**
     * 根据主键属性，判断主键是否值为空
     *
     * @param entity
     * @param field
     * @return 主键为空，则返回false；主键有值，返回true
     * @author 王浩彬
     * @date 2016年4月28日
     */
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }
}
