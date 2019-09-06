package com.zlsx.comzlsx.dto;

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

}
