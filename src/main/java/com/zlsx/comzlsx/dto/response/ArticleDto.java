package com.zlsx.comzlsx.dto.response;

import com.zlsx.comzlsx.domain.Article;
import lombok.Data;

/**
 * @author admin
 */
@Data
public class ArticleDto extends Article {
    private String avatar;
    private String nickName;
    private Boolean isPraise;
    private Boolean isKeep;
    private Integer praiseNum;
    private Integer commentNum;
    private Integer keepNum;
    private Integer browseNum;
    /**
     * 是否关注
     */
    private Boolean isAttented;
}
