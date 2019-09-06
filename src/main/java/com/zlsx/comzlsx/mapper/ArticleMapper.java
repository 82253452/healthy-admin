package com.zlsx.comzlsx.mapper;

import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.domain.Article;

import java.util.List;

import com.zlsx.comzlsx.dto.request.GetArticleListRequest;
import com.zlsx.comzlsx.dto.response.ArticleDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleMapper extends Mapper<Article> {
    int updateBatch(List<Article> list);

    int batchInsert(@Param("list") List<Article> list);

    int insertOrUpdate(Article record);

    int insertOrUpdateSelective(Article record);

    List<Article> selectallbyids(List<String> ids);

    ArticleDto selectArticleInfo(@Param("id") Integer id);

    List<ArticleDto> selectAllBuyUserOrderByTime(GetArticleListRequest page);

    List<ArticleDto> selectAllByAbout(PageInfo page);
}