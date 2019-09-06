package com.zlsx.comzlsx.util.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Project : kjserver;
 * @Program Name  : com.lzt.common.util.GriphDetailUtils.java;
 * @Class Name    : GriphDetailUtils;
 * @Description : 由HTML获取图文详情的工具类;
 * @Author : tangzhenzhi;
 * @Creation Date : 2018年8月26日 下午3:51:31 ;
 * @ModificationHistory ;
 * Who        When          What ;
 * --------   ----------    -----------------------------------;
 * username   2018年8月26日       TODO;
 */
public class GriphDetailUtils {


    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    private static final String IMGSRC_REG = "http[s]?:\\\"?(.*?)(\\\"|>|\\\\s+)"; //      [a-zA-z]+://[^\s]*


    public static List<String> griphDetail(String url) {
        try {
            String HTML = getHtml(url);
            List<String> imgUrl = getImageUrl(HTML);
            List<String> imgSrc = getImageSrc(imgUrl);
            return imgSrc;
        } catch (Exception e) {
            System.out.println("当前类GriphDetailUtils发生错误" + e);
            return null;
        }
    }

    public static List<String> getGriph(String html) {
        List<String> imgUrl = getImageUrl(html);
        return getImageSrc(imgUrl);
    }

    //获取HTML内容
    public static String getHtml(String url) throws Exception {
        URL url1 = new URL(url);
        URLConnection connection = url1.openConnection();
        InputStream in = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append(line, 0, line.length());
            sb.append('\n');
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }


    //获取ImageUrl地址
    private static List<String> getImageUrl(String html) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
        List<String> listimgurl = new ArrayList<String>();
        while (matcher.find()) {
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }

    //获取ImageSrc地址
    private static List<String> getImageSrc(List<String> listimageurl) {
        List<String> listImageSrc = new ArrayList<String>();
        for (String image : listimageurl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImageSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
            }
        }
        return listImageSrc;
    }
}
