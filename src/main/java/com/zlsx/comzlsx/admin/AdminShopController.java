package com.zlsx.comzlsx.admin;

import com.zlsx.comzlsx.domain.Shop;
import com.zlsx.comzlsx.mapper.ShopMapper;
import com.zlsx.comzlsx.service.ShopService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController()
@RequestMapping("/admin/shop")
public class AdminShopController {
    @Resource
    private ShopService shopService;
    @Resource
    private ShopMapper shopMapper;

    @GetMapping("/getShopById")
    public Result getShopById(String id) throws ForeseenException {
        Shop shop = shopMapper.selectByPrimaryKey(id);
        return Result.ok(shop);
    }
    @GetMapping("/getShopIndex")
    public Result getShopIndex() throws ForeseenException {
        List<Shop> shops = shopMapper.selectAll();
        return Result.ok(shops);
    }
}
