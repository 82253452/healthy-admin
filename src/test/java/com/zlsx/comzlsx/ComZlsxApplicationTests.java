package com.zlsx.comzlsx;

import com.zlsx.comzlsx.common.JWTUtils;
import com.zlsx.comzlsx.dto.enums.ExpireTimeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComZlsxApplicationTests {

    @Resource
    private JWTUtils jwtUtils;

    @Test
    public void contextLoads() {
    }

    @Test
    public void jwtToken() {
        String key = jwtUtils.createKey("9", "yp", "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLibOo23tSa8tUUxLe9uoBaVhjCNgLzLgZHzu9KGtS2ckuQ1FfhZ50GVCIQZnbUEXCPS4dfwXncbnQ/132", "", "oVTys5pNQ8I0BpneuVqThZnhygCo", ExpireTimeEnum.YEAR);
        System.out.println(key);
    }

}
