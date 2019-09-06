package com.zlsx.comzlsx.util.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author : houxm
 * @date : 2018/10/16 11:37
 * @description :
 */
public interface PageMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
