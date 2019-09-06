package com.zlsx.comzlsx.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`comment`")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 文章id
     */
    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "content")
    private String content;

    /**
     * 图片
     */
    @Column(name = "images")
    private String images;
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private Integer type;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}