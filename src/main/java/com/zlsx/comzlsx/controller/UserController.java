package com.zlsx.comzlsx.controller;

import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.UserInfo;
import com.zlsx.comzlsx.dto.response.UserInfoDto;
import com.zlsx.comzlsx.dto.request.GetUserListRequest;
import com.zlsx.comzlsx.mapper.UserInfoMapper;
import com.zlsx.comzlsx.service.UserInfoService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.web.bind.annotation.*;

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
    public Result login(String code, String appId) throws ForeseenException {
        UserInfoDto weChatUser = userInfoService.getWeChatUser(appId, code);
        return Result.ok(weChatUser);
    }

    @GetMapping("/loginWechat")
    public Result loginWechat() throws ForeseenException {
        String url = wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid("").oauth2buildAuthorizationUrl("localhost:8080", "snsapi_base", "test");
        return Result.ok(url);
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo() throws ForeseenException {
        UserInfoDto userInfoDto = userInfoService.getUserInfoDto(jwtUtils.getUserId());
        return Result.ok(userInfoDto);
    }

    @GetMapping("/list")
    public Result list(GetUserListRequest request) throws ForeseenException {
        PageInfo page = new PageInfo(userInfoMapper.selectUserList(request));
        return Result.ok(page);
    }

    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody UserInfo userInfo) throws ForeseenException {
        userInfoMapper.insertOrUpdateSelective(userInfo);
        return Result.ok();
    }

    @GetMapping("/delete/{id}")
    public Result deleteData(@PathVariable Integer id) throws ForeseenException {
        userInfoMapper.deleteByPrimaryKey(id);
        return Result.ok();
    }

    /**
     * 关注 取消关注
     *
     * @param id
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/attention/{id}")
    public Result attention(@PathVariable Integer id) throws ForeseenException {
        userInfoService.attentionUser(id);
        return Result.ok();
    }
}
