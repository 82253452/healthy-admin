package com.zlsx.comzlsx.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum SysTypeEnum {
    ANDROID(0, "ANDROID"),
    IOS(1, "IOS"),
    H5(3, "H5");

    private final Integer code;
    private final String description;

}
