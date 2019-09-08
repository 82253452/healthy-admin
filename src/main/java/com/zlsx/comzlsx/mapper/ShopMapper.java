package com.zlsx.comzlsx.mapper;

import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.domain.Shop;
import java.util.List;

import com.zlsx.comzlsx.dto.request.GetShopListRequest;
import com.zlsx.comzlsx.dto.response.ShopDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ShopMapper extends Mapper<Shop> {
    int updateBatch(List<Shop> list);

    int batchInsert(@Param("list") List<Shop> list);

    int insertOrUpdate(Shop record);

    int insertOrUpdateSelective(Shop record);

    List<ShopDto> selectShops(GetShopListRequest request);

    List<Shop> getArticleListAbout(PageInfo page);
}