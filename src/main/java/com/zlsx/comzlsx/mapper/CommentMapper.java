package com.zlsx.comzlsx.mapper;

import com.zlsx.comzlsx.domain.Comment;

import java.util.List;

import com.zlsx.comzlsx.dto.request.GetCommentListRequest;
import com.zlsx.comzlsx.dto.response.CommentDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CommentMapper extends Mapper<Comment> {
    int updateBatch(List<Comment> list);

    int batchInsert(@Param("list") List<Comment> list);

    int insertOrUpdate(Comment record);

    int insertOrUpdateSelective(Comment record);

    List<CommentDto> selectCommontByTree(GetCommentListRequest request);

    Comment getChildList(@Param("id") String id);
}