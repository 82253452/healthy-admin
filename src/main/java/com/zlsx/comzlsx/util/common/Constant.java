package com.zlsx.comzlsx.util.common;

/**
 * @author : houxm
 * @date : 2019/3/31 9:29
 * @description :
 */
public class Constant {

  /** 默认头像 */
  public static final String DEFAULT_AVATAR =
      "https://th.zhonglanmedia.com/html/kjwebapp/activity/community/img/default.png";

  /** 老系统用户来源本（系统慎用，只用来比较） 来源 0APP，1微博，2微信，3QQ，4华为 */
  public static final class Origin {
    public static final int phone = 0;
    public static final int weibo = 1;
    public static final int weixin = 2;
    public static final int qq = 3;
    public static final int wxpub = 4;
    public static final int sys_gen = 5; // 系统生成
    public static final int wxxcx = 6; // 微信小程序
  }

  public static final class SESSION_KEY {
    public static final String user = "sessionkey_user";
  }

  /** 资源类型 */
  public static final class ResType {
    public static final int VIDEO = 0; // 视频
    public static final int AUDIO = 1; // 音频
    public static final int ARTICLE = 2; // 文章
    public static final int USER = 3; // 用户
    public static final int QA = 4; // 回答
    public static final int Album = 5; // 专辑
    public static final int Goods = 6; // 商品
    public static final int StoreIndex = 7; // 货栈首页
    public static final int URL = 8;
    public static final int SUBJECT = 9; // 社区话题
    public static final int TOPIC = 10; // 观点
    public static final int IMG = 11; // 图片
    public static final int VIDEO_READY = 100; // 待更新视频
    public static final int Column = 12; // 栏目
  }

  public static final class UPYUN {
    public static final String DOMAINOLD = "zhonglanapp.b0.upaiyun.com";
    public static final String DOMAIN = "kjniu.zhonglanmedia.com";
    public static final String TOKEN = "m8odelan7dsy5stype0versiontgb1";
  }

  /** 后台使用 */
  public static int PW_ENCORDER_SALT = 12;

  public static final String RESOURCE_TYPE_MENU = "menu";
  public static final String RESOURCE_TYPE_BTN = "button";

  /** 分享视频地址 */
  public static final String SHARE_URL =
      "http://th.zhonglanmedia.com/kj/share/video/video_share.html?type=0&id=";
  /** 评论类型 */
  // 文章
  public static final Integer COMMENT_ARTICLE = 1;
  // 评论
  public static final Integer COMMENT_COMMENT = 2;
  // 收藏
  public static final Integer COMMENT_COLLECT = 3;
  // 视频点赞
  public static final Integer VIDEO_PRAISE = 4;
  // cosplay 帖子点赞
  public static final Integer COSPLAY_PRAISE = 5;
  // cosplay 评论点赞
  public static final Integer COSPLAY_COMMENT_PRAISE = 6;

  public static final String ES_INDEX = "onlone_zlsx_api_info_log_new";
}
