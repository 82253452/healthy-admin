package com.zlsx.comzlsx.dto.response;

import com.zlsx.comzlsx.domain.Shop;
import lombok.Data;

/**
 * @author admin
 */
@Data
public class ShopDto extends Shop {
    private String classifyName;
    private String addressName;
}
