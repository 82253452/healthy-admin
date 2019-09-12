package com.zlsx.comzlsx.controller;

import com.github.pagehelper.PageInfo;
import com.zlsx.comzlsx.domain.Shop;
import com.zlsx.comzlsx.domain.ShopOffer;
import com.zlsx.comzlsx.dto.request.GetShopListRequest;
import com.zlsx.comzlsx.dto.response.ArticleDto;
import com.zlsx.comzlsx.dto.response.ShopDto;
import com.zlsx.comzlsx.mapper.ShopMapper;
import com.zlsx.comzlsx.mapper.ShopOfferMapper;
import com.zlsx.comzlsx.service.ShopService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource
    private ShopService shopService;
    @Resource
    private ShopMapper shopMapper;
    @Resource
    private ShopOfferMapper shopOfferMapper;

    @GetMapping("/getShopById/{id}")
    public Result getShopById(@PathVariable String id) throws ForeseenException {
        Shop shop = shopMapper.selectByPrimaryKey(id);
        ShopDto shopDto = new ShopDto();
        BeanUtils.copyProperties(shop,shopDto);
        ShopOffer shopOffer = new ShopOffer();
        shopOffer.setShopId(shop.getId());
        List<ShopOffer> select = shopOfferMapper.select(shopOffer);
        shopDto.setOfferList(select);
        return Result.ok(shopDto);
    }

    @GetMapping("/getShopIndex")
    public Result getShopIndex(GetShopListRequest request) throws ForeseenException {
        List<ShopDto> shops = shopMapper.selectShops(request);
        shops.forEach(shop -> {
            ShopOffer shopOffer = new ShopOffer();
            shopOffer.setShopId(shop.getId());
            List<ShopOffer> select = shopOfferMapper.select(shopOffer);
            shop.setOfferList(select);
        });
        return Result.ok(shops);
    }

    @GetMapping("/list/about")
    public Result articleListAbout(PageInfo page) throws ForeseenException {
        List<Shop> articles = shopMapper.getArticleListAbout(page);
        return Result.ok(articles);
    }

    @GetMapping("/delete/{id}")
    public Result deleteData(@PathVariable Integer id) throws ForeseenException {
        shopMapper.deleteByPrimaryKey(id);
        return Result.ok();
    }

    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Shop shop) throws ForeseenException {
        shopMapper.insertOrUpdateSelective(shop);
        return Result.ok(shop);
    }

    @PostMapping("/saveShopOffer")
    public Result saveShopOffer(@RequestBody List<ShopOffer> shopOffer) throws ForeseenException {
        shopOffer.forEach(offer -> shopOfferMapper.insertOrUpdate(offer));
        return Result.ok();
    }
}
