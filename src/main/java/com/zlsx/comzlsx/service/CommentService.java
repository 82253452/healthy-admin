package com.zlsx.comzlsx.service;

import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.domain.Article;
import com.zlsx.comzlsx.domain.Comment;
import com.zlsx.comzlsx.dto.enums.CommentEnum;
import com.zlsx.comzlsx.dto.enums.SystemErrorEnum;
import com.zlsx.comzlsx.dto.request.GetCommentListRequest;
import com.zlsx.comzlsx.dto.request.GetCommentRequest;
import com.zlsx.comzlsx.dto.response.CommentDto;
import com.zlsx.comzlsx.mapper.ArticleMapper;
import com.zlsx.comzlsx.mapper.CommentMapper;
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
public class CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JWTUtils jwtUtils;

    public int updateBatch(List<Comment> list) {
        return commentMapper.updateBatch(list);
    }


    public int batchInsert(List<Comment> list) {
        return commentMapper.batchInsert(list);
    }


    public int insertOrUpdate(Comment record) {
        return commentMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Comment record) {
        return commentMapper.insertOrUpdateSelective(record);
    }

    /**
     * 评论列表
     *
     * @param request
     * @return
     */
    public PageInfo<CommentDto> getCommentList(GetCommentListRequest request) {
        PageInfo<CommentDto> pageInfo = new PageInfo<>(commentMapper.selectCommontByTree(request));
        pageInfo.getList().forEach(commentDto -> {
            commentDto.setStar(stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.COMMENT_USER_PRAISE, jwtUtils.getUserIdWithNoExce()), commentDto.getId().toString()));
            Object o = Optional.ofNullable(stringRedisTemplate.opsForHash().get(CacheKey.COMMENT_BROWSE_PRAISE, commentDto.getId().toString())).orElse("0");
            commentDto.setPraiseNum(Integer.valueOf(o.toString()));
        });
        return pageInfo;
    }

    /**
     * 增加评论
     *
     * @param request
     * @throws ShowException
     */
    public void addCommont(GetCommentRequest request) throws ForeseenException {
        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(request.getPid())) {
            Optional.ofNullable(commentMapper.selectByPrimaryKey(request.getPid())).orElseThrow(() -> new ShowException(SystemErrorEnum.NOT_FOUND_INDEX));
        }
        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(request.getTopicId())) {
            Article article = articleMapper.selectByPrimaryKey(request.getTopicId());
            if (article != null) {
                //增加评论数
                stringRedisTemplate.opsForHash().increment(CacheKey.ARTICLE_USER_COMMENT, request.getTopicId().toString(), 1);
            }
        }

        //增加评论
        addCommentDomain(request);
    }

    private void addCommentDomain(GetCommentRequest request) throws ForeseenException {
        Comment comment = new Comment();
        comment.setUserId(jwtUtils.getUserId());
        comment.setArticleId(request.getTopicId());
        comment.setContent(request.getContent());
        comment.setImages(request.getImages());
        comment.setParentId(request.getPid());
        comment.setType(request.getType());
        comment.setDeleted(false);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        commentMapper.insertSelective(comment);
    }

    public void praise(Integer id) throws ForeseenException {
        Optional.ofNullable(commentMapper.selectByPrimaryKey(id)).orElseThrow(() -> new ShowException(SystemErrorEnum.NOT_FOUND_INDEX));
        //增加评论点赞 取消点赞
        if (BooleanUtils.isTrue(stringRedisTemplate.opsForSet().isMember(String.format(CacheKey.COMMENT_USER_PRAISE, jwtUtils.getUserId()), id.toString()))) {
            stringRedisTemplate.opsForHash().increment(CacheKey.COMMENT_BROWSE_PRAISE, id.toString(), -1);
            stringRedisTemplate.opsForSet().remove(String.format(CacheKey.COMMENT_USER_PRAISE, jwtUtils.getUserId()), id.toString());
        } else {
            stringRedisTemplate.opsForHash().increment(CacheKey.COMMENT_BROWSE_PRAISE, id.toString(), 1);
            stringRedisTemplate.opsForSet().add(String.format(CacheKey.COMMENT_USER_PRAISE, jwtUtils.getUserId()), id.toString());
        }
    }
}
