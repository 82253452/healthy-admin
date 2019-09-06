package com.zlsx.comzlsx.dto.request;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : yp
 */
@Data
public class GetCommentListRequest extends PageInfo {
    @NotNull(message = "id不能为空")
    private String id;
    private Integer type;
}
