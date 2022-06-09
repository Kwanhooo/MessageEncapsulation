package com.hades.encap.controller;

import com.hades.encap.constant.IPv6HeaderTypes;
import com.hades.encap.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EncapsulationHandler {
    private FixedHeader fixedHeader;

    /**
     * 主控
     *
     * @param tcpData TCP数据
     * @return 二进制的IPv6报文数据
     * @author Kwanho
     */
    public String handle(@NotNull String tcpData) {
        // 构造IPv6报头
        fixedHeader = getFixedIPv6Header(tcpData);
        log.debug("basicHeader: {}", fixedHeader);
        // 构造IPv6拓展报头
        List<ExtendedHeader> extendedHeaders = getIPv6ExtendedHeaders();
        log.info("extendedHeaders: {}", extendedHeaders);
        for (ExtendedHeader extendedHeader : extendedHeaders) {
            log.debug("extendedHeader: {}", extendedHeader);
        }
        // 插入有效载荷
        return insertPayload(fixedHeader, extendedHeaders, tcpData);
    }

    /**
     * 生成一个IPv6头部
     *
     * @param tcpData 原始TCP数据
     * @return IPv6头部
     * @author Kwanho
     */
    @SuppressWarnings("SpellCheckingInspection")
    private FixedHeader getFixedIPv6Header(@NotNull String tcpData) {
        FixedHeader header = new FixedHeader();
        header.setHeaderType(IPv6HeaderTypes.ENCAPSULATED_IPv6_HEADER);
        header.setVersion(0b0110);//4
        header.setTrafficClass(0b000000_0_0);//8
        header.setFlowLabel(0b1010_1010_1010_1010_1010);//20
        header.setPayloadLength(tcpData.length());//16
        header.setNextHeader(null);//8
        header.setHopLimit(255);//8
//        header.setSourceAddress(new IPAddress("0123:4567:89ab:cdef:0123:4567:89ab:cdef"));//128
//        header.setDestinationAddress(new IPAddress("0123:4567:89ab:cdef:0123:4567:89ab:cdef"));//128
//

        header.setSourceAddress(new IPAddress(Application.srcIP));
        header.setDestinationAddress(new IPAddress(Application.dstIP));

        return header;
    }

    /**
     * 生成指定数量的IPv6拓展头部
     *
     * @return IPv6拓展头部列表
     * @author Kwanho
     */
    private List<ExtendedHeader> getIPv6ExtendedHeaders() {
        List<ExtendedHeader> headers = new ArrayList<>();
        ExtendedHeader header;
        int amountToGenerate = (int) (Math.random() * 4);
        for (int i = 0; i < amountToGenerate; i++) {
            int rand = (int) (Math.random() * 4);
            String preNextHeader = "";
            switch (rand) {
                case 0 -> {
                    log.info("生成 => AuthenticationHeader");
                    header = new AuthenticationHeader();
                    preNextHeader = "00110011";
                }
                case 1 -> {
                    log.info("生成 => FragmentHeader");
                    header = new FragmentHeader();
                    preNextHeader = "00101100";
                }
                case 2 -> {
                    log.info("生成 => DestinationOptionsHeader");
                    header = new OptionsPerHopHeader();
                    preNextHeader = "00000000";
                }
                case 3 -> {
                    log.info("生成 => RoutingHeader");
                    header = new RouteHeader();
                    preNextHeader = "00101011";
                }
                default -> {
                    log.info("生成 => UnknownHeader");
                    header = null;
                }
            }

            headers.add(header);

            if (i == 0) {
                this.fixedHeader.setNextHeader(preNextHeader);
            } else {
                headers.get(i - 1).setNextHeader(preNextHeader);
            }
            if (i == amountToGenerate - 1) {
                headers.get(i).setNextHeader("00000110");
            }
        }
        return headers;
    }

    /**
     * 插入有效载荷
     *
     * @param basicHeader    基本头部
     * @param extendedHeader 拓展头部
     * @param tcpData        TCP数据
     * @return 二进制的IPv6报文数据
     * @author Kwanho
     */
    private String insertPayload(FixedHeader basicHeader, List<ExtendedHeader> extendedHeader, String tcpData) {
        // 输出基本头部
        StringBuilder finalData = new StringBuilder(getHeaderBinaryString(basicHeader));

        // 输出拓展头部
        for (ExtendedHeader header : extendedHeader) {
            finalData.append(header.toString());
        }

        String binaryTCPData;
        // 如果传入的是16进制的话，把tcp数据转换成二进制字符串
        if (tcpData.matches("^[0-9a-fA-F]+$")) {
            log.info("传入的是16进制的TCP数据");
            binaryTCPData = getFixedBinaryString(tcpData, tcpData.length() * 4);
        } else {
            log.info("传入的是二进制的TCP数据");
            binaryTCPData = tcpData;
        }

        log.info("二进制TCP数据: {}", binaryTCPData);
        finalData.append(binaryTCPData);
        return finalData.toString();
    }

    /**
     * 获取固定位数的二进制字符串
     *
     * @param number       原始数字
     * @param neededLength 需要的二进制字符串长度
     * @return 二进制字符串
     * @author Kwanho
     */
    public static String getFixedBinaryString(Long number, int neededLength) {
        String originalStr = Long.toBinaryString(number);
        return ("0".repeat(Math.max(0, neededLength - originalStr.length())) +
                originalStr);
    }

    /**
     * 获取固定位数的二进制字符串
     *
     * @param number       原始数字
     * @param neededLength 需要的二进制字符串长度
     * @return 二进制字符串
     * @author Himxs
     */
    public static String getFixedBinaryString(String number, int neededLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            switch (number.charAt(i)) {
                case '0' -> sb.append("0000");
                case '1' -> sb.append("0001");
                case '2' -> sb.append("0010");
                case '3' -> sb.append("0011");
                case '4' -> sb.append("0100");
                case '5' -> sb.append("0101");
                case '6' -> sb.append("0110");
                case '7' -> sb.append("0111");
                case '8' -> sb.append("1000");
                case '9' -> sb.append("1001");
                case 'a' -> sb.append("1010");
                case 'b' -> sb.append("1011");
                case 'c' -> sb.append("1100");
                case 'd' -> sb.append("1101");
                case 'e' -> sb.append("1110");
                case 'f' -> sb.append("1111");
                default -> System.out.println("error");
            }
        }
        sb.append("0", 0, neededLength - sb.length());
        return sb.toString();
    }

    /**
     * 将IPv6头部转换成二进制字符串
     *
     * @param header 头部
     * @return 二进制字符串
     */
    private String getHeaderBinaryString(FixedHeader header) {
        StringBuilder sb = new StringBuilder();
        String version = getFixedBinaryString(header.getVersion(), 4);
        log.info("version: " + version);
        sb.append(version);
        String trafficClass = getFixedBinaryString(header.getTrafficClass(), 8);
        log.info("trafficClass: " + trafficClass);
        sb.append(trafficClass);
        String flowLabel = getFixedBinaryString(header.getFlowLabel(), 20);
        log.info("flowLabel: " + flowLabel);
        sb.append(flowLabel);
        String payloadLength = getFixedBinaryString((long) header.getPayloadLength(), 16);
        log.info("payloadLength: " + payloadLength);
        sb.append(payloadLength);
        String nextHeader = header.getNextHeader();
        log.info("nextHeader: " + nextHeader);
        sb.append(nextHeader);
        String hopLimit = getFixedBinaryString((long) header.getHopLimit(), 8);
        log.info("hopLimit: " + hopLimit);
        sb.append(hopLimit);
        String sourceAddress = header.getSourceAddress().getBinaryAddress();


        log.info("sourceAddress: " + sourceAddress);
        sb.append(sourceAddress);


        String destinationAddress = header.getDestinationAddress().getBinaryAddress();
        StringBuffer ss = new StringBuffer();
        ss.append("0".repeat(Math.max(0, 128 - destinationAddress.length())));
        ss.append(sourceAddress);
        destinationAddress = ss.toString();


        log.info("destinationAddress: " + destinationAddress);
        sb.append(destinationAddress);
        return sb.toString();
    }


}
