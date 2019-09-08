package com.zlsx.comzlsx.dto.request;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetArticleListRequest extends PageInfo {
    private Integer uid;
    private Integer type;
    private Integer classifyId;
    private Integer searchType;
    private List<String> ids;
    private Boolean isMember;
}
