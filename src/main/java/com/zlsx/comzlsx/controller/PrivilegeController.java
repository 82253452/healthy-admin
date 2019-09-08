package com.zlsx.comzlsx.controller;

import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.util.common.CacheKey;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JWTUtils jwtUtils;

    @GetMapping("/info")
    public Result infoPrivilege() throws ForeseenException {
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(CacheKey.PRIVILEGE_LIST);
        Object o = stringRedisTemplate.opsForHash().get(CacheKey.PRIVILEGE_LIST_USER, jwtUtils.getUserIdWithNoExce());
        entries.put("status",o);
        return Result.ok(entries);
    }

    @GetMapping("/receive/{id}")
    public Result receivePrivilege(@PathVariable Integer id) throws ForeseenException {
        Boolean aBoolean = stringRedisTemplate.opsForHash().hasKey(CacheKey.PRIVILEGE_LIST_USER, jwtUtils.getUserId().toString());
        if (aBoolean) {
            return Result.ok();
        }
        Object o = stringRedisTemplate.opsForHash().get(CacheKey.PRIVILEGE_LIST, id.toString());
        if (o == null) {
            return Result.ok();
        }
        stringRedisTemplate.opsForHash().put(CacheKey.PRIVILEGE_LIST_USER, jwtUtils.getUserId().toString(), o.toString());
        return Result.ok();
    }

    @GetMapping("/get")
    public Result getPrivilege() throws ForeseenException {
        Object o = stringRedisTemplate.opsForHash().get(CacheKey.PRIVILEGE_LIST_USER, jwtUtils.getUserId());
        return Result.ok(o);
    }

}
