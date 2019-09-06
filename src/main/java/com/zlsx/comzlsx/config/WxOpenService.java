package com.zlsx.comzlsx.config;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
@EnableConfigurationProperties({WeiXinOpenConfig.class})
public class WxOpenService extends WxOpenServiceImpl {
    @Resource
    private WeiXinOpenConfig weiXinOpenConfig;
    @Resource
    private RedisProperties redisProperies;
    private static JedisPool pool;

    @PostConstruct
    public void init() {
        WxOpenInRedisConfigStorage wxOpenInMemoryConfigStorage = new WxOpenInRedisConfigStorage(getJedisPool());
        wxOpenInMemoryConfigStorage.setComponentAppId(weiXinOpenConfig.getComponentAppId() == null ? "" : weiXinOpenConfig.getComponentAppId());
        wxOpenInMemoryConfigStorage.setComponentAppSecret(weiXinOpenConfig.getComponentSecret() == null ? "" : weiXinOpenConfig.getComponentSecret());
        wxOpenInMemoryConfigStorage.setComponentToken(weiXinOpenConfig.getComponentToken() == null ? "" : weiXinOpenConfig.getComponentToken());
        wxOpenInMemoryConfigStorage.setComponentAesKey(weiXinOpenConfig.getComponentAesKey() == null ? "" : weiXinOpenConfig.getComponentAesKey());
        setWxOpenConfigStorage(wxOpenInMemoryConfigStorage);
    }

    private JedisPool getJedisPool() {
        if (pool == null) {
            pool = new JedisPool(new GenericObjectPoolConfig(), redisProperies.getHost(), redisProperies.getPort(), 2000, redisProperies.getPassword());
        }
        return pool;
    }

}
