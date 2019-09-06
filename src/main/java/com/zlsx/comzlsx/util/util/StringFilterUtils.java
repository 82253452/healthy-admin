package com.zlsx.comzlsx.util.util;

import com.zlsx.comzlsx.util.common.ShowException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : houxm
 * @date : 2019/2/27 11:26
 * @description :
 */
public class StringFilterUtils {
  private static SensitiveWord sw = new SensitiveWord("CensorWords.txt");

  public static String filterHtmlAndSensitive(String str) {
    str = filterHtml(str);
    return filterSensitive(str);
  }

  public static String filterHtml(String inputString) {
    if (StringUtils.isEmpty(inputString)) {
      return null;
    }
    String htmlStr = inputString; // 含html标签的字符串
    String textStr = "";
    Pattern p_script;
    Matcher m_script;
    Pattern p_style;
    Matcher m_style;
    Pattern p_html;
    Matcher m_html;
    Pattern p_html1;
    Matcher m_html1;
    try {
      String regEx_script =
          "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
      String regEx_style =
          "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
      String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
      String regEx_html1 = "<[^>]+";
      p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
      m_script = p_script.matcher(htmlStr);
      htmlStr = m_script.replaceAll(""); // 过滤script标签

      p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
      m_style = p_style.matcher(htmlStr);
      htmlStr = m_style.replaceAll(""); // 过滤style标签

      p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
      m_html = p_html.matcher(htmlStr);
      htmlStr = m_html.replaceAll(""); // 过滤html标签

      p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
      m_html1 = p_html1.matcher(htmlStr);
      htmlStr = m_html1.replaceAll(""); // 过滤html标签
      textStr = htmlStr;
    } catch (Exception e) {

    }
    return textStr; // 返回文本字符串
  }

  public static String filterSensitive(String inputString) {
    if (StringUtils.isEmpty(inputString)) {
      return null;
    }
    return sw.filterInfo(inputString);
  }

  public static Boolean containSensitiveWords(String inputString) {
    if (StringUtils.isBlank(inputString)) {
      return false;
    }
    return sw.containSensitiveWords(inputString);
  }

  public static String CheckContentWithExp(String content) throws ShowException {
    // 过滤脚本和敏感词
    Boolean containSensitiveWords = StringFilterUtils.containSensitiveWords(content);
    if (containSensitiveWords) {
      throw new ShowException("包含敏感词！");
    }
    return content;
  }
}
