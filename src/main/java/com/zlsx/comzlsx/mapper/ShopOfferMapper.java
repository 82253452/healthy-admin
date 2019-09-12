package com.zlsx.comzlsx.mapper;

import com.zlsx.comzlsx.domain.ShopOffer;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ShopOfferMapper extends Mapper<ShopOffer> {
    int updateBatch(List<ShopOffer> list);

    int batchInsert(@Param("list") List<ShopOffer> list);

    int insertOrUpdate(ShopOffer record);

    int insertOrUpdateSelective(ShopOffer record);
}