package com.zlsx.comzlsx.util.util;//package com.fast4ward.buildworldapi.util;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.Pipeline;
//import redis.clients.jedis.exceptions.JedisException;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @author : houxm
// * @date : 2018/9/5 15:41
// * @description :
// */
//public abstract class JedisTools {
//    public abstract int getDBIndex();
//
//    public static Logger logger = LoggerFactory.getLogger(JedisTools.class);
//
//    protected String getKey(String prefix, String suffix) {
//        return prefix.replaceAll("\\s*", "") + "_" + getClass().getName().replace(".", "_") + "_" + suffix.replaceAll("\\s*", "");
//    }
//
//    protected String getMethodName() {
//        return getClass().getName().replace(".", "_");
//    }
//
//    @Resource
//    protected JedisPool jedisPool;
//
//    /**
//     * Jedis实例获取
//     *
//     * @return
//     */
//    protected Jedis getJedis() {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//        } catch (JedisException e) {
//            logger.info("获取Jedis报错，Info: {}", e.getMessage());
//            throw e;
//        }
//        return jedis;
//    }
//
//    /**
//     * Jedis实例回收
//     *
//     * @param jedis
//     * @param isBroken
//     */
//    protected void returnJedis(Jedis jedis, boolean isBroken) {
//        if (jedis != null) {
//            if (isBroken) {
//                jedisPool.returnBrokenResource(jedis);
//            } else {
//                jedisPool.returnResource(jedis);
//            }
//        }
//    }
//
//    /**
//     * 缓存键值对
//     *
//     * @param key
//     * @param value
//     * @param methodName
//     * @return
//     */
//    protected byte[] addByteToJedis(byte[] key, byte[] value, String methodName) {
//        return addByteToJedis(key, value, 0, methodName);
//    }
//
//    /**
//     * 缓存键值对
//     *
//     * @param key
//     * @param value
//     * @param seconds
//     * @param methodName
//     * @return
//     */
//    protected byte[] addByteToJedis(byte[] key, byte[] value, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        byte[] lastVal = null;
//
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            lastVal = jedis.getSet(key, value);
//            if (seconds != 0) {
//                jedis.expire(key, seconds);
//            }
//            logger.info("缓存键值对，状态[{}]，key:{},value:{},seconds:{},methodName:{}", "Success", key, value, seconds, methodName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            isBroken = true;
//            logger.info("缓存键值对，状态[{}]，key:{},value:{},seconds:{},methodName:{}", "Failure", key, value, seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//
//        return lastVal;
//    }
//
//    /**
//     * 缓存键值对
//     *
//     * @param key
//     * @param value
//     * @param methodName
//     * @return
//     */
//    protected String addStringToJedis(String key, String value, String methodName) {
//        return addStringToJedis(key, value, 0, methodName);
//    }
//
//    /**
//     * 缓存键值对
//     *
//     * @param key
//     * @param value
//     * @param seconds
//     * @param methodName
//     * @return
//     */
//    protected String addStringToJedis(String key, String value, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        String lastVal = null;
//
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            lastVal = jedis.getSet(key, value);
//            if (seconds != 0) {
//                jedis.expire(key, seconds);
//            }
//            logger.info("缓存键值对，状态[{}]，key:{},value:{},seconds:{},methodName:{}", "Success", key, value, seconds, methodName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            isBroken = true;
//            logger.info("缓存键值对，状态[{}]，key:{},value:{},seconds:{},methodName:{}", "Failure", key, value, seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//
//        return lastVal;
//    }
//
//    /**
//     * 缓存Map集合（多键值对）
//     *
//     * @param batchData
//     * @param methodName
//     */
//    protected void addStringToJedis(Map<String, String> batchData, String methodName) {
//        addStringToJedis(batchData, 0, methodName);
//    }
//
//    /**
//     * 缓存Map集合（多键值对）
//     *
//     * @param batchData
//     * @param seconds
//     * @param methodName
//     */
//    protected void addStringToJedis(Map<String, String> batchData, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            Pipeline pipeline = jedis.pipelined();
//            for (Map.Entry<String, String> element : batchData.entrySet()) {
//                if (seconds != 0) {
//                    pipeline.setex(element.getKey(), seconds, element.getValue());
//                } else {
//                    pipeline.set(element.getKey(), element.getValue());
//                }
//            }
//            pipeline.sync();
//            logger.info("缓存Map集合（多键值对），状态[{}]，num:{},seconds:{},methodName:{}", "Success", batchData.size(), seconds, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存Map集合（多键值对），状态[{}]，num:{},seconds:{},methodName:{}", "Failure", batchData.size(), seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存列表数据
//     *
//     * @param key
//     * @param list
//     * @param methodName
//     */
//    protected void addListToJedis(String key, List<String> list, String methodName) {
//        addListToJedis(key, list, 0, methodName);
//    }
//
//    /**
//     * 缓存列表数据
//     *
//     * @param key
//     * @param str
//     * @param seconds
//     * @param methodName
//     */
//    protected void addListSringToJedisNoDel(String key, String str, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.rpush(key, str);
//            if (seconds != 0) {
//                jedis.expire(key, seconds);
//            }
//            logger.info("缓存列表数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, str, seconds, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存列表数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, str, seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存列表数据
//     *
//     * @param key
//     * @param list
//     * @param seconds
//     * @param methodName
//     */
//    protected void addListToJedis(String key, List<String> list, int seconds, String methodName) {
//        if (list != null && list.size() > 0) {
//            Jedis jedis = null;
//            boolean isBroken = false;
//            try {
//                jedis = this.getJedis();
//                jedis.select(getDBIndex());
//
//                if (jedis.exists(key)) {
//                    jedis.del(key);
//                }
//                for (String aList : list) {
//                    jedis.rpush(key, aList);
//                }
//                if (seconds != 0) {
//                    jedis.expire(key, seconds);
//                }
//
//                logger.info("缓存列表数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(list), seconds, methodName);
//            } catch (Exception e) {
//                isBroken = true;
//                logger.info("缓存列表数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(list), seconds, methodName);
//            } finally {
//                this.returnJedis(jedis, isBroken);
//            }
//        }
//    }
//
//    /**
//     * 缓存Set集合数据
//     *
//     * @param key
//     * @param value
//     * @param methodName
//     */
//    protected void addSetToJedis(String key, String[] value, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.sadd(key, value);
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(value), 0, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(value), 0, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存Set集合数据
//     *
//     * @param key
//     * @param methodName
//     */
//    protected void addZSetToJedis(String key, String k2, double score, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.zadd(key, score, k2);
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(k2), 0, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(k2), 0, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    protected Double getZScore(String key, String k2) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            return jedis.zscore(key, k2);
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//    /**
//     * 缓存Set集合数据
//     *
//     * @param key
//     * @param methodName
//     */
//    protected void incrSetToJedis(String key, String k2, double score, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.zincrby(key, score, k2);
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(k2), 0, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(k2), 0, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存Set集合数据
//     *
//     * @param key
//     */
//    protected Set<String> getZSetRange(String key, long start, long end) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            return jedis.zrange(key, start, end);
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//    protected Set<String> getVZrevSetRange(String key, long start, long end) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            return jedis.zrevrange(key, start, end);
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//    protected Set<String> zrangeByScore(String key, double start, double end) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            return jedis.zrangeByScore(key, start, end);
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//    protected Long zrank(String key, String key2) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            return jedis.zrevrank(key, key2);
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//
//    /**
//     * 缓存Set集合数据
//     *
//     * @param key
//     * @param methodName
//     */
//    protected void delZSetToJedis(String key, String k2, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.zrem(key, k2);
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(k2), 0, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(k2), 0, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 判断Set中是否有value
//     *
//     * @param key
//     * @param value
//     * @param methodName
//     */
//    protected boolean isMemberSetToJedis(String key, String value, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//
//        boolean isMember = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            isMember = jedis.sismember(key, value);
//
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(value), 0, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(value), 0, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//
//        return isMember;
//    }
//
//    /**
//     * 删除Set集合数据
//     *
//     * @param key
//     * @param value
//     * @param methodName
//     */
//    protected void removeSetJedis(String key, String[] value, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.srem(key, value);
//            logger.info("删除Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(value), 0, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("删除Set集合数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(value), 0, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存列表中添加数据
//     *
//     * @param key
//     * @param value
//     * @param methodName
//     */
//    protected void pushDataToList(String key, String value, String methodName) {
//        pushDataToList(key, value, 0, methodName);
//    }
//
//    /**
//     * 缓存列表中添加数据
//     *
//     * @param key
//     * @param value
//     * @param seconds
//     * @param methodName
//     */
//    protected void pushDataToList(String key, String value, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.rpush(key, value);
//            if (seconds != 0) {
//                jedis.expire(key, seconds);
//            }
//            logger.info("缓存列表中添加数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(value), seconds, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存列表中添加数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(value), seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存列表中批量添加数据
//     *
//     * @param key
//     * @param data
//     * @param methodName
//     */
//    protected void pushDataToList(String key, String[] data, String methodName) {
//        pushDataToList(key, data, 0, methodName);
//    }
//
//    /**
//     * 缓存列表中批量添加数据
//     *
//     * @param key
//     * @param data
//     * @param seconds
//     * @param methodName
//     */
//    protected void pushDataToList(String key, String[] data, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.lpush(key, data);
//            for (String value : data) {
//                jedis.rpush(key, value);
//            }
//            if (seconds != 0) {
//                jedis.expire(key, seconds);
//            }
//            logger.info("缓存列表中批量添加数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(data), seconds, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存列表中批量添加数据，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(data), seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 删除list中的元素
//     *
//     * @param key
//     * @param data
//     * @param methodName
//     */
//    protected void deleteDataFromListJedis(String key, String[] data, String methodName) {
//        deleteDataFromListJedis(key, data, 0, methodName);
//    }
//
//    /**
//     * 删除list中的元素
//     *
//     * @param key
//     * @param data
//     * @param seconds
//     * @param methodName
//     */
//    protected void deleteDataFromListJedis(String key, String[] data, int seconds, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            Pipeline pipelined = jedis.pipelined();
//            if (data != null && data.length > 0) {
//                for (String value : data) {
//                    pipelined.lrem(key, 0, value);
//                }
//            }
//            pipelined.sync();
//            logger.info("删除list中的元素，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(data), seconds, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("删除list中的元素，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(data), seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 缓存HashMap
//     *
//     * @param key
//     * @param map
//     * @param isModified
//     * @param methodName
//     */
//    protected void addHashMapToJedis(String key, Map<String, String> map, boolean isModified, String methodName) {
//        addHashMapToJedis(key, map, 0, isModified, methodName);
//    }
//
//    /**
//     * 缓存HashMap
//     *
//     * @param key
//     * @param map
//     * @param seconds
//     * @param isModified
//     * @param methodName
//     */
//    protected void addHashMapToJedis(String key, Map<String, String> map, int seconds, boolean isModified, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        if (map != null && map.size() > 0) {
//            try {
//                jedis = this.getJedis();
//                jedis.select(getDBIndex());
//                jedis.hmset(key, map);
//                if (seconds != 0) {
//                    jedis.expire(key, seconds);
//                }
//                logger.info("缓存HashMap，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, JSON.toJSONString(map), seconds, methodName);
//            } catch (Exception e) {
//                isBroken = true;
//                logger.info("缓存HashMap，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, JSON.toJSONString(map), seconds, methodName);
//            } finally {
//                this.returnJedis(jedis, isBroken);
//            }
//        }
//    }
//
//    /**
//     * 向Map中添加键值对
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @param methodName
//     */
//    protected void addHashMapToJedis(String key, String field, String value, String methodName) {
//        addHashMapToJedis(key, field, value, 0, methodName);
//    }
//
//    /**
//     * 向Map中添加键值对
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @param seconds
//     * @param methodName
//     */
//    protected void addHashMapToJedis(String key, String field, String value, int seconds, String methodName) {
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            if (jedis != null) {
//                jedis.select(getDBIndex());
//                jedis.hset(key, field, value);
//                if (seconds != 0) {
//                    jedis.expire(key, seconds);
//                }
//                logger.info("缓存HashMap，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Success", key, seconds, methodName);
//            }
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存HashMap，状态[{}]，key:{}, list:{},seconds:{},methodName:{}", "Failure", key, seconds, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * Map 是否存在
//     *
//     * @param key
//     * @param field
//     * @param methodName
//     */
//    protected Boolean hasHashMap(String key, String field, String methodName) {
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            if (jedis != null) {
//                jedis.select(getDBIndex());
//                logger.info("缓存HashMap，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//                return jedis.hexists(key, field);
//            }
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("缓存HashMap，状态[{}]，key:{}, list:{},methodName:{}", "Failure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return false;
//    }
//
//    /**
//     * 向Map中添加键值对
//     *
//     * @param key
//     * @param field
//     * @param methodName
//     */
//    protected Long delHashMapMember(String key, String field, String methodName) {
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            if (jedis != null) {
//                jedis.select(getDBIndex());
//                logger.info("删除HashMap，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//                return jedis.hdel(key, field);
//            }
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("删除HashMap，状态[{}]，key:{}, list:{},methodName:{}", "Failure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return 0L;
//    }
//
//    /**
//     * 不用
//     *
//     * @param key
//     * @param field
//     * @param value
//     * @param dateField
//     * @param defaultValue
//     * @param methodName
//     */
//    protected void updateHashMapToJedis(String key, String field, long value, String dateField, String defaultValue, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            jedis.hincrBy(key, field, value);
//            jedis.hset(key, dateField, defaultValue);
//            logger.info("获取HashMap，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("获取HashMap，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 通过Key获取value
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    public String getStringFromJedis(String key, String methodName) {
//        String value = null;
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            if (jedis.exists(key)) {
//                value = jedis.get(key);
//                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
//                logger.info("通过Key获取value，状态[{}]，key:{}, list:{},methodName:{},getDBIndex:{}", "Success", key, methodName, getDBIndex());
//            }
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("通过Key获取value，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return value;
//    }
//
//    /**
//     * 通过Key获取value
//     *
//     * @param key
//     * @param dbIndex
//     * @return
//     */
//    public String getStringFromJedis(String key, int dbIndex) {
//        String value = null;
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(dbIndex);
//            if (jedis.exists(key)) {
//                value = jedis.get(key);
//                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
//                logger.info("通过Key获取value，状态[{}]，key:{}, list:{}", "Success", key);
//            }
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return value;
//    }
//
//    /**
//     * 批量获取键值
//     *
//     * @param keys
//     * @param methodName
//     * @return
//     */
//    public List<String> getStringFromJedis(String[] keys, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            logger.info("批量获取键值，状态[{}]，key:{}, list:{},methodName:{}", "Success", keys, methodName);
//            return jedis.mget(keys);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("批量获取键值，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", keys, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//    /**
//     * 获取list集合
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    protected List<String> getListFromJedis(String key, String methodName) {
//        List<String> list = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            if (jedis.exists(key)) {
//                list = jedis.lrange(key, 0, -1);
//            }
//            logger.info("获取list集合，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//        } catch (JedisException e) {
//            isBroken = true;
//            logger.info("获取list集合，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return list;
//    }
//
//    /**
//     * @param key
//     * @param methodName
//     * @param delValue   取出值后是否清空库
//     * @return
//     */
//    protected List<byte[]> getListFromJedis(byte[] key, String methodName, boolean delValue) {
//        List<byte[]> list = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            if (jedis.exists(key)) {
//                list = jedis.lrange(key, 0, -1);
//                if (delValue) {
//                    jedis.ltrim(key, 1, 0); // 全部删除
//                }
//            }
//            logger.info("获取list集合，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//        } catch (JedisException e) {
//            isBroken = true;
//            logger.info("获取list集合，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return list;
//    }
//
//    /**
//     * 获取Set集合
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    protected Set<String> getSetFromJedis(String key, String methodName) {
//        Set<String> list = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            if (jedis.exists(key)) {
//                list = jedis.smembers(key);
//            }
//            logger.info("获取Set集合，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("获取Set集合，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return list;
//    }
//
//    /**
//     * 获取HashMap集合
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    protected Map<String, String> getHashMapFromJedis(String key, String methodName) {
//        Map<String, String> hashMap = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            hashMap = jedis.hgetAll(key);
//            logger.info("获取HashMap集合，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("获取HashMap集合，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return hashMap;
//    }
//
//    /**
//     * 获取HashMap中某个field的值
//     *
//     * @param key
//     * @param field
//     * @param methodName
//     * @return
//     */
//    protected String getHashMapValueFromJedis(String key, String field, String methodName) {
//        String value = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            if (jedis != null) {
//                jedis.select(getDBIndex());
//                if (jedis.exists(key)) {
//                    value = jedis.hget(key, field);
//                }
//            }
//            logger.info("获取HashMap中某个field的值，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, field, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("获取HashMap中某个field的值，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, field, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return value;
//    }
//
//    /**
//     * 获取HashMap中某个field的值
//     *
//     * @param key
//     * @param field
//     * @param methodName
//     * @return
//     */
//    protected String getHashMapValueFromJedis(String key, String field, int dbIndex, String methodName) {
//        String value = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            if (jedis != null) {
//                jedis.select(dbIndex);
//                if (jedis.exists(key)) {
//                    value = jedis.hget(key, field);
//                }
//            }
//            logger.info("获取HashMap中某个field的值，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, field, methodName);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("获取HashMap中某个field的值，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, field, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return value;
//    }
//
//    /**
//     * Redis生成自增的主键
//     *
//     * @param identifyName
//     * @param methodName
//     * @return
//     */
//    public Long getIdentifyId(String identifyName, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        Long identify = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            identify = jedis.incr(identifyName);
//            if (identify == 0) {
//                return jedis.incr(identifyName);
//            } else {
//                return identify;
//            }
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("Redis生成自增的主键，状态[{}],methodName:{}", "Failure");
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return null;
//    }
//
//    /**
//     * 删除置顶的Key值
//     *
//     * @param key
//     * @return
//     */
//    public Long delKeyFromJedis(String key) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            logger.info("清空Index指向的DB，状态[{}],methodName:{}", "Success");
//            return jedis.del(key);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("清空Index指向的DB，状态[{}],methodName:{}", "Failure");
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return 0L;
//    }
//
//    /**
//     * 清空Index指向的DB
//     *
//     * @param index
//     */
//    public void flushDBFromJedis(int index) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//
//        try {
//            jedis = this.getJedis();
//            jedis.select(index);
//            jedis.flushDB();
//            logger.info("清空Index指向的DB，状态[{}],methodName:{}", "Success");
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("清空Index指向的DB，状态[{}],methodName:{}", "Failure");
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//    }
//
//    /**
//     * 判断Key是否存在
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    public boolean existKey(String key, String methodName) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            logger.info("判断Key是否存在，状态[{}]，key:{},methodName:{}", "Success", key, methodName);
//            return jedis.exists(key);
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("判断Key是否存在，状态[{}]，key:{},methodName:{}", "Failure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return false;
//    }
//
//    public Long generatorKey(String key) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//
//        Long value = 0l;
//
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//
//            if (jedis.exists(key)) {
//                value = jedis.incr(key);
//            } else {
//                value = 1l;
//                jedis.set(key, "1");
//            }
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return value;
//    }
//
//    public boolean hasKey(String key) {
//        Jedis jedis = null;
//        boolean isBroken = false;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            return jedis.exists(key);
//        } catch (Exception e) {
//            isBroken = true;
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return false;
//    }
//
//    /**
//     * redis值增加
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    protected Long hIncrBy(String key, String key2, long v, String methodName) {
//        Map<String, String> hashMap = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            Long l = jedis.hincrBy(key, key2, v);
//            logger.info("redis值增加，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//            return l;
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("redis值增加，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return 0L;
//    }
//
//    /**
//     * redis值增加
//     *
//     * @param key
//     * @param methodName
//     * @return
//     */
//    protected Long incrBy(String key, long v, String methodName) {
//        Map<String, String> hashMap = null;
//        boolean isBroken = false;
//        Jedis jedis = null;
//        try {
//            jedis = this.getJedis();
//            jedis.select(getDBIndex());
//            Long l = jedis.incrBy(key, v);
//            logger.info("redis值增加，状态[{}]，key:{}, list:{},methodName:{}", "Success", key, methodName);
//            return l;
//        } catch (Exception e) {
//            isBroken = true;
//            logger.info("redis值增加，状态[{}]，key:{}, list:{},methodName:{}", "Faliure", key, methodName);
//        } finally {
//            this.returnJedis(jedis, isBroken);
//        }
//        return 0L;
//    }
//
//    public static void main(String[] args) {
//        JedisTools tool = new JedisTools() {
//            @Override
//            public int getDBIndex() {
//                return 0;
//            }
//        };
//        System.out.println(tool.hasHashMap("mykey", "123", "123"));
//        tool.addHashMapToJedis("mykey", "123", "123", 200, "123");
//        System.out.println(tool.hasHashMap("mykey", "123", "123"));
//    }
//}
//
