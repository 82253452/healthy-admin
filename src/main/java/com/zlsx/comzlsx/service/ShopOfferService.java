package com.zlsx.comzlsx.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.zlsx.comzlsx.domain.ShopOffer;
import java.util.List;
import com.zlsx.comzlsx.mapper.ShopOfferMapper;
@Service
public class ShopOfferService{

    @Resource
    private ShopOfferMapper shopOfferMapper;

    
    public int updateBatch(List<ShopOffer> list) {
        return shopOfferMapper.updateBatch(list);
    }

    
    public int batchInsert(List<ShopOffer> list) {
        return shopOfferMapper.batchInsert(list);
    }

    
    public int insertOrUpdate(ShopOffer record) {
        return shopOfferMapper.insertOrUpdate(record);
    }

    
    public int insertOrUpdateSelective(ShopOffer record) {
        return shopOfferMapper.insertOrUpdateSelective(record);
    }

}
