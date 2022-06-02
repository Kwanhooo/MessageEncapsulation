package com.hades.encap.constant;

/**
 * IPv6头部类型码 枚举类
 * 请使用它来进行报头类型的标识
 *
 * @author kwanho
 */
public enum IPv6HeaderTypes {
    HOP_BY_HOP_OPTIONS_HEADER(0, "Hop-by-Hop Options Header"),
    TCP(6, "TCP"),
    UDP(17, "UDP"),
    ENCAPSULATED_IPv6_HEADER(41, "Encapsulated IPv6 Header"),
    ROUTING_HEADER(43, "Routing Header"),
    FRAGMENT_HEADER(44, "Fragment Header"),
    ENCAPSULATING_SECURITY_PAYLOAD_HEADER(50, "Encapsulating Security Payload Header"),
    AUTHENTICATION_HEADER(51, "Authentication Header"),
    ICMPv6(58, "ICMPv6"),
    NO_NEXT_HEADER(59, "No Next Header"),
    DESTINATION_OPTIONS_HEADER(60, "Destination Options Header");

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
