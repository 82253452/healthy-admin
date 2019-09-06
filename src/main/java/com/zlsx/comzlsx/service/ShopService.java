package com.zlsx.comzlsx.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.zlsx.comzlsx.mapper.ShopMapper;
import com.zlsx.comzlsx.domain.Shop;
import java.util.List;
@Service
public class ShopService{

    @Resource
    private ShopMapper shopMapper;

    
    public int updateBatch(List<Shop> list) {
        return shopMapper.updateBatch(list);
    }

    
    public int batchInsert(List<Shop> list) {
        return shopMapper.batchInsert(list);
    }

    
    public int insertOrUpdate(Shop record) {
        return shopMapper.insertOrUpdate(record);
    }

    
    public int insertOrUpdateSelective(Shop record) {
        return shopMapper.insertOrUpdateSelective(record);
    }

}
