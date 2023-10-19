package cn.com.xcsa.gateway.util;

public enum SourceEnum {
    LDAP("LDAP"),
    DINGDING("dingding"),
    WEIXIN("weixin"),
    FEISHU("feishu"),
    LOCAL("local");

    private final String value;

    SourceEnum(String varValue) {
        this.value = varValue;
    }

    /**
     * 获取值.
     * @return 值.
     */
    public String getValue() {
        return value;
    }
}
