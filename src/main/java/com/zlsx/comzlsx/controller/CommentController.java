package com.zlsx.comzlsx.controller;

import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.dto.request.GetCommentListRequest;
import com.zlsx.comzlsx.dto.request.GetCommentRequest;
import com.zlsx.comzlsx.dto.response.CommentDto;
import com.zlsx.comzlsx.service.CommentService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("/addComment")
    public Result addComment(@RequestBody @Valid GetCommentRequest request) throws ForeseenException {
        commentService.addCommont(request);
        return Result.ok();
    }

    @GetMapping("/comments")
    public Result comments(@Valid GetCommentListRequest request) throws ForeseenException {
        PageInfo<CommentDto> commont = commentService.getCommentList(request);
        return Result.ok(commont);
    }
    @GetMapping("/praise/{id}")
    public Result praise(@PathVariable Integer id) throws ForeseenException {
        commentService.praise(id);
        return Result.ok();
    }
}
