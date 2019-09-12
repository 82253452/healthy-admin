package com.zlsx.comzlsx.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "shop_offer")
public class ShopOffer {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 说明
     */
    @Column(name = "description")
    private String description;

    /**
     * 现价
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 原价
     */
    @Column(name = "old_price")
    private BigDecimal oldPrice;

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private Integer shopId;

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
}