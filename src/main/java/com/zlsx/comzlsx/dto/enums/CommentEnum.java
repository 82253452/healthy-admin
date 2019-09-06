package com.zlsx.comzlsx.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum CommentEnum {
    COMMENT(1, "COMMENT");
    private final Integer code;
    private final String description;

}
