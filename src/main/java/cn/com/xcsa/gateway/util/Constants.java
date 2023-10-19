package cn.com.xcsa.gateway.util;

import cn.hutool.core.util.StrUtil;

public class Constants {
    public final static String CAPTCHA = "captcha";
    public final static String BUSINESS_AUTH = "auth";
    public final static String BUSINESS_LOGIN = "login";
    public final static String BUSINESS_CAPTCHA = "captcha";

    public final static String TYPE_TOTP = "totp";
    public static final String TYPE_ACCOUNT = "account";
    public static final String TYPE_SMS = "SMS";
    public static final String TYPE_EMAIL = "email";


    /**
     * 根据业务类型 分类 变量获取指定key.
     * @param business
     * @param type
     * @param variable
     * @return key.
     */
    public static String genTokenKey(String business, String type, String variable) {
        return business + StrUtil.COLON + type + StrUtil.COLON + variable;
    }
}
