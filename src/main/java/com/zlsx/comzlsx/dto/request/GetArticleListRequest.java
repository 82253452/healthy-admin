package com.zlsx.comzlsx.dto.request;

import com.github.pagehelper.PageInfo;
import lombok.Data;

@Data
public class GetArticleListRequest extends PageInfo {
    private Integer uid;
    private Integer type;
}
