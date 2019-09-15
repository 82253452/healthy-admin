package com.zlsx.comzlsx.service;

import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.Comment;
import com.zlsx.comzlsx.domain.UserInfo;
import com.zlsx.comzlsx.dto.enums.SystemErrorEnum;
import com.zlsx.comzlsx.dto.response.UserInfoDto;
import com.zlsx.comzlsx.dto.enums.ExpireTimeEnum;
import com.zlsx.comzlsx.mapper.CommentMapper;
import com.zlsx.comzlsx.mapper.UserInfoMapper;
import com.zlsx.comzlsx.util.common.CacheKey;
import com.zlsx.comzlsx.util.common.ForeseenException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.open.api.WxOpenService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CommentMapper commentMapper;

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

    public UserInfoDto getUserInfoDto(Integer userId) throws ForeseenException {
        UserInfo userInfo = Optional.ofNullable(userInfoMapper.selectByPrimaryKey(userId)).orElseThrow(() -> new ForeseenException(SystemErrorEnum.USER_EXP));
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo, userInfoDto);
        //获取用户收藏 点赞数
        Long size = stringRedisTemplate.opsForSet().size(String.format(CacheKey.ARTICLE_USER_PRAISE, userId));
        userInfoDto.setUserPraiseNum(size);
        //获取用户评论数
        Comment comment = new Comment();
        comment.setUserId(userId);
        int count = commentMapper.selectCount(comment);
        //获取用户分享数
        Object o = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_USER_SHARE, userId)).orElse(0);
        userInfoDto.setUserShareNum(Long.valueOf(o.toString()));
        //获取用户浏览记录数
        Long size1 = stringRedisTemplate.opsForSet().size(String.format(CacheKey.ARTICLE_USER_BROWSE, userId));
        userInfoDto.setUserBrowseNum(size1);
        //获取用户粉丝
        Long size2 = stringRedisTemplate.opsForSet().size(String.format(CacheKey.ARTICLE_USER_FAN, userId));
        userInfoDto.setUserFenNums(size2);
        //获取用户关注
        Long size3 = stringRedisTemplate.opsForSet().size(String.format(CacheKey.ARTICLE_USER_ATTENTION, userId));
        userInfoDto.setUserAttentionNums(size3);
        //获取用户被浏览量
        Object o1 = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_USER_VIEWED, userId.toString())).orElse(0);
        userInfoDto.setUserViewedNums(Long.valueOf(o1.toString()));
        return userInfoDto;
    }


    public void attentionUser(Integer id) throws ForeseenException {
        if (BooleanUtils.isTrue(stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_ATTENTION, jwtUtils.getUserId()), id.toString()))) {
            //加关注缓存
            stringRedisTemplate.opsForSet().remove(String.format(CacheKey.ARTICLE_USER_ATTENTION, id.toString()), jwtUtils.getUserId().toString());
            //加粉丝缓存
            stringRedisTemplate.opsForSet().remove(String.format(CacheKey.ARTICLE_USER_FAN, jwtUtils.getUserId()), id.toString());
        } else {
            stringRedisTemplate.opsForSet().add(String.format(CacheKey.ARTICLE_USER_ATTENTION, jwtUtils.getUserId()), id.toString());
            stringRedisTemplate.opsForSet().add(String.format(CacheKey.ARTICLE_USER_FAN, id.toString()), jwtUtils.getUserId().toString());
        }
    }
}
