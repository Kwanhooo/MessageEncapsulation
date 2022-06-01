package com.hades.encap.constant;

/**
 * IPv6头部类型码 枚举类
 * 请使用它来进行报头类型的标识
 *
 * @author kwanho
 */
public enum IPv6HeaderTypes {
    SIMPLE(1000, "普通报头"),
    HOP_BY_HOP(1001, "逐跳选项头部"),
    ROUTING(1002, "路由头部"),
    DESTINATION(1003, "目的选项头部"),
    AUTHENTICATION(1004, "认证头部"),
    SECURITY(1005, "封装安全载荷头部");

    /**
     * 类型码
     */
    private final int code;

    /**
     * 类型码信息
     */
    private final String description;


    IPv6HeaderTypes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
