package com.zlsx.comzlsx.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "classify")
public class Classify {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 分类名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 类型
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 父级标签
     */
    @Column(name = "pid")
    private Integer pid;

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
    private String image;
}