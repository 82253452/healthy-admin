package com.zlsx.comzlsx.mapper;

import com.zlsx.comzlsx.domain.Address;
import java.util.List;

import com.zlsx.comzlsx.domain.Comment;
import com.zlsx.comzlsx.dto.response.AddressDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AddressMapper extends Mapper<Address> {
    int updateBatch(List<Address> list);

    int batchInsert(@Param("list") List<Address> list);

    int insertOrUpdate(Address record);

    int insertOrUpdateSelective(Address record);

    List<AddressDto> selectTreeList(Address address);

    Address getChildList(@Param("id") String id);
}