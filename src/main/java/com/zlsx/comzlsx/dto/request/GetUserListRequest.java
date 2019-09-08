package com.zlsx.comzlsx.dto.request;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetUserListRequest extends PageInfo {
    private Integer uid;
}
