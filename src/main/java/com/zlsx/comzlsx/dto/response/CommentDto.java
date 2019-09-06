package com.zlsx.comzlsx.dto.response;

import com.zlsx.comzlsx.domain.Comment;
import lombok.Data;

import java.util.List;

@Data
public class CommentDto extends Comment {

    private Boolean star = false;
    private Integer praiseNum;

    private Boolean myComment = false;

    private String avatar;

    private String nickName;

    private String targetUserId;

    private String targetUserPhoto;

    private String targetUserName;

    private Integer childCount = 0;

    private List<CommentDto> childList;

    private String exclude;
}
