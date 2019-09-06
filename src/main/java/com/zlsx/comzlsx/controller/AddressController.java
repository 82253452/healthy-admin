package com.zlsx.comzlsx.controller;

import com.zlsx.comzlsx.domain.Address;
import com.zlsx.comzlsx.dto.response.AddressDto;
import com.zlsx.comzlsx.mapper.AddressMapper;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource
    private AddressMapper addressMapper;

    @GetMapping("/list/fir")
    public Result list() throws ForeseenException {
        Address address = new Address();
        address.setType(1);
        List<Address> select = addressMapper.select(address);
        return Result.ok(select);
    }

    @GetMapping("/list/tree")
    public Result tree() throws ForeseenException {
        Address address = new Address();
        address.setType(1);
        List<AddressDto> select = addressMapper.selectTreeList(address);
        return Result.ok(select);
    }

    @GetMapping("/list/sec/{id}")
    public Result sec(@PathVariable Integer id) throws ForeseenException {
        Address address = new Address();
        address.setPid(id);
        List<Address> select = addressMapper.select(address);
        return Result.ok(select);
    }

    @GetMapping("/delete/{id}")
    public Result deleteData(@PathVariable Integer id) throws ForeseenException {
        addressMapper.deleteByPrimaryKey(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Address shop) throws ForeseenException {
        addressMapper.insertOrUpdateSelective(shop);
        return Result.ok();
    }

}
