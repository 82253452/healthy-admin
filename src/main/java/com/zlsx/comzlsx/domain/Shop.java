package com.zlsx.comzlsx.domain;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "shop")
public class Shop {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 店名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 图片
     */
    @Column(name = "images")
    private String images;

    /**
     * 介绍
     */
    @Column(name = "Introduction")
    private String introduction;

    /**
     * 经度
     */
    @Column(name = "Latitude")
    private String latitude;

    /**
     * 纬度
     */
    @Column(name = "longitude")
    private String longitude;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除
     */
    @Column(name = "deleted")
    private Boolean deleted;

    private String image;
    @Column(name = "classify_id")
    private Integer classifyId;
    @Column(name = "address_id")
    private Integer addressId;

    /**
     * 人均
     */
    private Integer percapita;
    /**
     * 评分
     */
    private Integer rate;
}