package com.zlsx.comzlsx.service;

import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.UserInfo;
import com.zlsx.comzlsx.dto.UserInfoDto;
import com.zlsx.comzlsx.dto.enums.ExpireTimeEnum;
import com.zlsx.comzlsx.mapper.UserInfoMapper;
import com.zlsx.comzlsx.util.common.ForeseenException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private JWTUtils jwtUtils;


    public int batchInsert(List<UserInfo> list) {
        return userInfoMapper.batchInsert(list);
    }


    public int insertOrUpdate(UserInfo record) {
        return userInfoMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserInfo record) {
        return userInfoMapper.insertOrUpdateSelective(record);
    }

    public UserInfoDto getWeChatUser(String appId, String code) throws ForeseenException {
        WxMpService wxMpService = wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId);
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            WxMpUser user = wxMpService.oauth2getUserInfo(accessToken, null);
            UserInfo userInfo = new UserInfo();
            userInfo.setUnionId(user.getUnionId());
            userInfo = Optional.ofNullable(userInfoMapper.selectOne(userInfo)).orElse(new UserInfo());
            userInfo.setUnionId(user.getUnionId());
            userInfo.setNickName(user.getNickname());
            userInfo.setAvatar(user.getHeadImgUrl());
            userInfo.setPhone("");
            userInfo.setBirthday(new Date());
            userInfo.setSex(user.getSex().byteValue());
            userInfo.setAccountBalance(new BigDecimal("0"));
            userInfo.setIntegration(0);
            userInfo.setStatus((byte) 0);
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfo.setDeleted(false);
            userInfoMapper.insertOrUpdateSelective(userInfo);
            String xToken =
                    jwtUtils.createKey(
                            String.valueOf(userInfo.getId()),
                            userInfo.getNickName(),
                            userInfo.getPhone(),
                            null,
                            user.getOpenId(),
                            ExpireTimeEnum.HOUR);
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUserName(userInfo.getNickName());
            userInfoDto.setPhotoUrl(userInfo.getAvatar());
            userInfoDto.setToken(xToken);
            userInfoDto.setExpireTime(null);
            return userInfoDto;
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

}
