package com.zlsx.comzlsx.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 手机号
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 性别
     */
    @Column(name = "sex")
    private Byte sex;

    /**
     * 余额
     */
    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    /**
     * 用户积分
     */
    @Column(name = "integration")
    private Integer integration;

    /**
     * 会员过期时间
     */
    @Column(name = "vip_expire_time")
    private Date vipExpireTime;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private Byte status;

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
    private String introduction;
}