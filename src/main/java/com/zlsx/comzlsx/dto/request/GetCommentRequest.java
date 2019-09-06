package com.zlsx.comzlsx.dto.request;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** @author : yp */
@Data
public class GetCommentRequest extends PageInfo {

  @NotBlank(message = "内容不能为空")
  private String content;

  @NotNull(message = "类型不能为空")
  private Integer type;

  //回复某个人的评论
  private Integer pid;

  //评论文章
  private Integer topicId;

  private String images;

}
