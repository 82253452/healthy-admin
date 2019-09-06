package com.zlsx.comzlsx.controller;

import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.UserInfo;
import com.zlsx.comzlsx.dto.UserInfoDto;
import com.zlsx.comzlsx.mapper.UserInfoMapper;
import com.zlsx.comzlsx.service.UserInfoService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private JWTUtils jwtUtils;

    @GetMapping("/login")
    public Result login(String code) throws ForeseenException {
        UserInfoDto weChatUser = userInfoService.getWeChatUser("", code);
        return Result.ok(weChatUser);
    }

    @GetMapping("/loginWechat")
    public Result loginWechat() throws ForeseenException {
        String url = wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid("").oauth2buildAuthorizationUrl("localhost:8080", "snsapi_base", "test");
        return Result.ok(url);
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo() throws ForeseenException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(jwtUtils.getUserId());
        return Result.ok(userInfo);
    }
}
