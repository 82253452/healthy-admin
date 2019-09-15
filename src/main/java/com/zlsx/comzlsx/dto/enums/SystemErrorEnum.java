package com.zlsx.comzlsx.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum SystemErrorEnum {

    /**
     *
     */
    NOT_FOUND_INDEX(1001, "id 不存在"),
    USER_EXP(100, "用户信息异常"),

    HOUR(4, "小时");

    private final Integer code;
    private final String description;
}
