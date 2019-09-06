package com.zlsx.comzlsx.util.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : houxm
 * @date : 2019/3/19 17:41
 * @description :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

    public BaseEntity(Date createTime, Date updateTime, Boolean deleted) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleted = deleted;
    }
}
