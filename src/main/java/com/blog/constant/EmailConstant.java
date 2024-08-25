package com.blog.constant;

/**
 * @Author
 * @Date: 2024/03/03 11:24:40
 * @Version: 1.0
 * @Description: 电子邮件常量
 */
public interface EmailConstant {

    /**
     * 电子邮件html内容路径 resources目录下
     */
    String EMAIL_HTML_CONTENT_PATH = "email.html";

    /**
     * 电子邮件html支付成功路径
     */
    String EMAIL_HTML_PAY_SUCCESS_PATH = "pay.html";

    /**
     * captcha缓存键
     */
    String CAPTCHA_CACHE_KEY = "api:captcha:";

    /**
     * 电子邮件主题
     */
    String EMAIL_SUBJECT = "验证码邮件";

    /**
     * 电子邮件标题
     */
    String EMAIL_TITLE = "XXXXXXXXXXX";

    /**
     * 电子邮件标题英语
     */
    String EMAIL_TITLE_ENGLISH = "KUN-API Open Interface Platform";

    /**
     * 平台负责人
     */
    String PLATFORM_RESPONSIBLE_PERSON = "夏洛特";

    /**
     * 平台地址
     */
    String PLATFORM_ADDRESS = "<a href='http://XXXXXXXXXX/'>请联系我们</a>";

    String PATH_ADDRESS = "'http://XXXXXXXXXXXX/'";
}