package com.zlsx.comzlsx.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum ExpireTimeEnum {
    /**
     * 帖子点赞
     */
    YEAR(1, "年"),
    /**
     * 帖子点赞
     */
    HOUR(4, "小时");

    private final Integer code;
    private final String description;


}
