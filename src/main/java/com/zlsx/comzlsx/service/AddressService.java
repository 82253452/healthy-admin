package com.zlsx.comzlsx.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.zlsx.comzlsx.mapper.AddressMapper;
import com.zlsx.comzlsx.domain.Address;
@Service
public class AddressService{

    @Resource
    private AddressMapper addressMapper;

    
    public int updateBatch(List<Address> list) {
        return addressMapper.updateBatch(list);
    }

    
    public int batchInsert(List<Address> list) {
        return addressMapper.batchInsert(list);
    }

    
    public int insertOrUpdate(Address record) {
        return addressMapper.insertOrUpdate(record);
    }

    
    public int insertOrUpdateSelective(Address record) {
        return addressMapper.insertOrUpdateSelective(record);
    }

}
