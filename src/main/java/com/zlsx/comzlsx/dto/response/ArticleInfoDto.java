package com.zlsx.comzlsx.dto.response;

import com.zlsx.comzlsx.domain.Article;
import lombok.Data;

/**
 * @author admin
 */
@Data
public class ArticleInfoDto extends Article {
    private String avatar;
    private String nickName;
    private Boolean isPraise;
    private Integer praiseNum;
    private Integer commonts;
    private Integer keep;
}
