package com.zlsx.comzlsx.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.Article;
import com.zlsx.comzlsx.dto.enums.SystemErrorEnum;
import com.zlsx.comzlsx.dto.request.GetArticleListRequest;
import com.zlsx.comzlsx.dto.response.ArticleDto;
import com.zlsx.comzlsx.mapper.ArticleMapper;
import com.zlsx.comzlsx.util.common.CacheKey;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.ShowException;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JWTUtils jwtUtils;

    public int updateBatch(List<Article> list) {
        return articleMapper.updateBatch(list);
    }


    public int batchInsert(List<Article> list) {
        return articleMapper.batchInsert(list);
    }


    public int insertOrUpdate(Article record) {
        return articleMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Article record) {
        return articleMapper.insertOrUpdateSelective(record);
    }

    public ArticleDto getInfo(Integer id) throws ForeseenException {
        ArticleDto articleDto = Optional.ofNullable(articleMapper.selectArticleInfo(id)).orElseThrow(() -> new ShowException(SystemErrorEnum.NOT_FOUND_INDEX));
        //增加文章浏览量
        stringRedisTemplate.opsForHash().increment(CacheKey.ARTICLE_BROWSE_TOTLE, id.toString(), 1);
        //文章点赞数
        Object praiseNum = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_BROWSE_PRAISE, id.toString())).orElse("0");
        articleDto.setPraiseNum(Integer.valueOf(praiseNum.toString()));
        //文章评论数
        Object commentNum = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_USER_COMMENT, id.toString())).orElse("0");
        articleDto.setCommentNum(Integer.valueOf(commentNum.toString()));
        //文章收藏数
        Object keepNum = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_USER_KEEP, id.toString())).orElse("0");
        articleDto.setKeepNum(Integer.valueOf(keepNum.toString()));
        //文章浏览量
        Object browseNum = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_BROWSE_TOTLE, id.toString())).orElse("0");
        articleDto.setBrowseNum(Integer.valueOf(browseNum.toString()));
        //是否点赞
        Boolean isPraise = stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserIdWithNoExce()), articleDto.getId().toString());
        articleDto.setIsPraise(isPraise);
        //是否收藏
        Boolean isKeep = stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_KEEP, jwtUtils.getUserIdWithNoExce()), articleDto.getId().toString());
        articleDto.setIsKeep(isKeep);
        return articleDto;
    }

    public void praise(Integer id) throws ForeseenException {
        Optional.ofNullable(articleMapper.selectByPrimaryKey(id)).orElseThrow(() -> new ShowException(SystemErrorEnum.NOT_FOUND_INDEX));
        //增加文章点赞 取消点赞
        if (BooleanUtils.isTrue(stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserId()), id.toString()))) {
            stringRedisTemplate.opsForHash().increment(CacheKey.ARTICLE_BROWSE_PRAISE, id.toString(), -1);
            stringRedisTemplate.opsForSet().remove(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserId()), id.toString());
        } else {
            stringRedisTemplate.opsForHash().increment(CacheKey.ARTICLE_BROWSE_PRAISE, id.toString(), 1);
            stringRedisTemplate.opsForSet().add(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserId()), id.toString());
        }
    }

    public void keep(Integer id) throws ForeseenException {
        Optional.ofNullable(articleMapper.selectByPrimaryKey(id)).orElseThrow(() -> new ShowException(SystemErrorEnum.NOT_FOUND_INDEX));
        //增加文章收藏
        if (BooleanUtils.isTrue(stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_KEEP, jwtUtils.getUserId()), id.toString()))) {
            stringRedisTemplate.opsForHash().increment(CacheKey.ARTICLE_USER_KEEP, id.toString(), -1);
            stringRedisTemplate.opsForSet().remove(String.format(CacheKey.ARTICLE_USER_KEEP, jwtUtils.getUserId()), id.toString());
        } else {
            stringRedisTemplate.opsForHash().increment(CacheKey.ARTICLE_USER_KEEP, id.toString(), 1);
            stringRedisTemplate.opsForSet().add(String.format(CacheKey.ARTICLE_USER_KEEP, jwtUtils.getUserId()), id.toString());
        }
    }

    public List<Article> praiseList(Page page) throws ForeseenException {
        List<String> range = stringRedisTemplate.opsForList().range(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserId()), page.getStartRow(), page.getEndRow());
        List<Article> selectallbyids = articleMapper.selectallbyids(range);
        return selectallbyids;
    }

    public List<Article> keepList(Page page) throws ForeseenException {
        List<String> range = stringRedisTemplate.opsForList().range(String.format(CacheKey.ARTICLE_USER_KEEP, jwtUtils.getUserId()), page.getStartRow(), page.getEndRow());
        List<Article> selectallbyids = articleMapper.selectallbyids(range);
        return selectallbyids;
    }

    public List<ArticleDto> getArticleList(GetArticleListRequest request) throws ForeseenException {
        List<ArticleDto> articles = articleMapper.selectAllBuyUserOrderByTime(request);
        articles.forEach(article -> {
            article.setIsPraise(stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserIdWithNoExce()), article.getId().toString()));
            Object o = stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_BROWSE_PRAISE, article.getId().toString());
            article.setPraiseNum(o == null ? 0 : Integer.valueOf(o.toString()));
        });
        return articles;
    }

    public List<ArticleDto> getArticleListAbout(PageInfo page) throws ForeseenException {
        List<ArticleDto> articles = articleMapper.selectAllByAbout(page);
        articles.forEach(articleDto -> {
            //文章点赞数
            Object praiseNum = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.ARTICLE_BROWSE_PRAISE, articleDto.getId().toString())).orElse("0");
            articleDto.setPraiseNum(Integer.valueOf(praiseNum.toString()));
            //是否点赞
            Boolean isPraise = stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.ARTICLE_USER_PRAISE, jwtUtils.getUserIdWithNoExce()), articleDto.getId().toString());
            articleDto.setIsPraise(isPraise);
        });
        return articles;
    }

    public void saveArticle(Article article) throws ForeseenException {
        article.setUserId(jwtUtils.getUserId());
        article.setStatus(0);
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setDeleted(false);
        articleMapper.insertSelective(article);
    }
}
