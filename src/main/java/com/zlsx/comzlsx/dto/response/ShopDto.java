package com.zlsx.comzlsx.dto.response;

import com.zlsx.comzlsx.domain.Shop;
import com.zlsx.comzlsx.domain.ShopOffer;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 */
@Data
public class ShopDto extends Shop {
    private String classifyName;
    private String addressName;
    private List<ShopOffer> offerList;
    private Long commontsNum;
}
