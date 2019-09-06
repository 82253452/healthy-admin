package com.zlsx.comzlsx.util.common;


/**
 * @author admin
 */
public class CacheKey {

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_BROWSE_TOTLE = "ARTICLE_BROWSE_TOTLE";
    /**
     * 文章点赞
     * ARTICLE_USER_PRAISE_USERID
     */
    public static final String ARTICLE_USER_PRAISE = "ARTICLE_USER_PRAISE_%s";
    /**
     * 文章点赞数
     */
    public static final String ARTICLE_BROWSE_PRAISE = "ARTICLE_BROWSE_PRAISE";
    /**
     * 文章收藏
     */
    public static final String ARTICLE_BROWSE_KEEP = "ARTICLE_BROWSE_KEEP";
    /**
     * 评论点赞数
     * COMMENT_BROWSE_PRAISE
     */
    public static final String COMMENT_BROWSE_PRAISE = "COMMENT_BROWSE_PRAISE";
    /**
     * 评论点赞
     * COMMENT_USER_PRAISE
     */
    public static final String COMMENT_USER_PRAISE = "COMMENT_USER_PRAISE_%s";
    /**
     * 文章收藏
     * ARTICLE_USER_KEEP_USERID
     */
    public static final String ARTICLE_USER_KEEP = "ARTICLE_USER_KEEP_%s";

    /**
     * 文章评论数
     * ARTICLE_USER_COMMENT
     */
    public static final String ARTICLE_USER_COMMENT = "ARTICLE_USER_COMMENT";

}
