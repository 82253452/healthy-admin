package com.zlsx.comzlsx.controller;

import com.qiniu.util.Auth;
import com.zlsx.comzlsx.config.WxOpenService;
import com.zlsx.comzlsx.util.common.ForeseenException;
import com.zlsx.comzlsx.util.common.Result;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private WxOpenService wxOpenService;

    private final static String accessKey = "QGwnW_OYnSO3QR2osJ2mwqManZECHvFZ1tPq8bAv";
    private final static String secretKey = "xQzpa36UhxHWj6PXHgNJJeGiFQifbOxVLDZ5zWue";
    private final static String bucket = "avatar";

    private final static String xiehe_accessKey = "s966eYvuU4QrRmsC1CQieLLIQByZHfnzywbnZUTA";
    private final static String xiehe_secretKey = "0wgZtZK-FNWVqhMf7JdFh6XXxBjiScnvnXoemGLQ";
    private final static String xiehe_bucket = "xiehehealth";
    @GetMapping("/getQiniuToken")
    public Result getQiniuToken() throws ForeseenException {
        Auth auth = Auth.create(xiehe_accessKey, xiehe_secretKey);
        String upToken = auth.uploadToken(xiehe_bucket);
        return Result.ok(upToken);
    }
    @GetMapping("/jsapiSignature")
    public Result weixinJSDk(String appId,String url) throws ForeseenException, WxErrorException {
        WxJsapiSignature jsapiSignature = wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).createJsapiSignature(url);
        return Result.ok(jsapiSignature);
    }
}
