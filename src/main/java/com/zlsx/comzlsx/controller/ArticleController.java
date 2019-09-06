package com.zlsx.comzlsx.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.Article;
import com.zlsx.comzlsx.dto.request.GetArticleListRequest;
import com.zlsx.comzlsx.dto.response.ArticleDto;
import com.zlsx.comzlsx.mapper.ArticleMapper;
import com.zlsx.comzlsx.service.ArticleService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleService articleService;
    @Resource
    private JWTUtils jwtUtils;

    @GetMapping("/list")
    public Result articleList(GetArticleListRequest request) throws ForeseenException {
        List<ArticleDto> articles = articleService.getArticleList(request);
        return Result.ok(articles);
    }

    @GetMapping("/list/user")
    public Result articleListUser(GetArticleListRequest request) throws ForeseenException {
        request.setUid(jwtUtils.getUserId());
        List<ArticleDto> articles = articleService.getArticleList(request);
        return Result.ok(articles);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Article article) throws ForeseenException {
        articleService.saveArticle(article);
        return Result.ok();
    }

    @GetMapping("/list/about")
    public Result articleListAbout(PageInfo page) throws ForeseenException {
        List<ArticleDto> articles = articleService.getArticleListAbout(page);
        return Result.ok(articles);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable Integer id) throws ForeseenException {
        ArticleDto article = articleService.getInfo(id);
        return Result.ok(article);
    }

    @GetMapping("/praise/{id}")
    public Result praise(@PathVariable Integer id) throws ForeseenException {
        articleService.praise(id);
        return Result.ok();
    }

    @GetMapping("/keep/{id}")
    public Result keep(@PathVariable Integer id) throws ForeseenException {
        articleService.keep(id);
        return Result.ok();
    }

    @GetMapping("/praise/list")
    public Result praiseList(Page page) throws ForeseenException {
        List<Article> articles = articleService.praiseList(page);
        return Result.ok(articles);
    }

    @GetMapping("/keep/list")
    public Result keepList(Page page) throws ForeseenException {
        List<Article> articles = articleService.keepList(page);
        return Result.ok(articles);
    }
}
