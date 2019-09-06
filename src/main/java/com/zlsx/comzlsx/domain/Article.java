package com.zlsx.comzlsx.domain;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "article")
public class Article {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * userid
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 发帖内容
     */
    @Column(name = "context")
    private String context;

    /**
     * 发帖图片
     */
    @Column(name = "images")
    private String images;

    /**
     * 发帖图片
     */
    @Column(name = "image")
    private String image;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "video")
    private String video;
    private String title;
    private Integer type;
}