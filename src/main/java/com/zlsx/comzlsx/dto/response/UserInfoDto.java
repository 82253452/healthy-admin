package com.zlsx.comzlsx.dto.response;

import lombok.Data;


/**
 * @author admin
 */
@Data
public class UserInfoDto {
    private String userName;

    private String photoUrl;

    private String token;

    private String expireTime;
    /**
     * 用户点赞数
     */
    private Long userPraiseNum;
    /**
     * 用户评论数
     */
    private Long userCommentsNum;
    /**
     * 用户分享数
     */
    private Long userShareNum;
    /**
     * 用户浏览记录
     */
    private Long userBrowseNum;
}
