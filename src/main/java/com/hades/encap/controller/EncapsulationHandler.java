package com.hades.encap.controller;

import com.hades.encap.constant.IPv6HeaderTypes;
import com.hades.encap.entity.IPAddress;
import com.hades.encap.entity.IPv6Header;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EncapsulationHandler {
    private IPv6Header basicHeader;

    public String handle(@NotNull String tcpData) {
        // 构造IPv6报头
        basicHeader = getIPv6Header(tcpData);
        log.debug("basicHeader: {}", basicHeader);
        // 构造IPv6拓展报头
        List<IPv6Header> extendedHeaders = getIPv6ExtendedHeaders(tcpData);
        log.debug("extendedHeaders: {}", extendedHeaders);
        // 插入有效载荷
        return insertPayload(basicHeader, extendedHeaders, tcpData);
    }

    private IPv6Header getIPv6Header(@NotNull String tcpData) {
        IPv6Header header = new IPv6Header();
        header.setHeaderType(IPv6HeaderTypes.ENCAPSULATED_IPv6_HEADER);
        header.setVersion(0b0110);//4
        header.setTrafficClass(0b000000_0_0);//8
        header.setFlowLabel(0b1010_1010_1010_1010_1010);//20
        header.setPayloadLength(tcpData.length());//16
        header.setNextHeader(null);//8
        header.setHopLimit(255);//8
        header.setSourceAddress(new IPAddress("0123:4567:89ab:cdef:0123:4567:89ab:cdef"));//128
        header.setDestinationAddress(new IPAddress("0123:4567:89ab:cdef:0123:4567:89ab:cdef"));//128
        return header;
    }

    private List<IPv6Header> getIPv6ExtendedHeaders(@NotNull String tcpData) {
        List<IPv6Header> extendedHeaders = new ArrayList<>();
        IPv6Header lastHeader = basicHeader;
        for (int i = 0; i < 1; i++) {
            IPv6Header header = getIPv6Header(tcpData);
            if (lastHeader != null)
                lastHeader.setNextHeader(header);
            header.setHeaderType(IPv6HeaderTypes.values()[i]);
            extendedHeaders.add(header);
            lastHeader = header;
        }
        return extendedHeaders;
    }

    private String insertPayload(IPv6Header basicHeader, List<IPv6Header> extendedHeader, String tcpData) {
        String sb = getHeaderBinaryString(basicHeader);

        // 把tcp数据转换成二进制字符串
        String binaryString = getFixedBinaryString(tcpData, tcpData.length() * 4);
        return sb;
    }

    public static String getFixedBinaryString(Long number, int neededLength) {
        String originalStr = Long.toBinaryString(number);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < neededLength - originalStr.length(); i++) {
            sb.append("0");
        }
        sb.append(originalStr);
        return (sb.toString());
    }

    public static String getFixedBinaryString(String number, int neededLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            switch (number.charAt(i)) {
                case '0':
                    sb.append("0000");
                    break;
                case '1':
                    sb.append("0001");
                    break;
                case '2':
                    sb.append("0010");
                    break;
                case '3':
                    sb.append("0011");
                    break;
                case '4':
                    sb.append("0100");
                    break;
                case '5':
                    sb.append("0101");
                    break;
                case '6':
                    sb.append("0110");
                    break;
                case '7':
                    sb.append("0111");
                    break;
                case '8':
                    sb.append("1000");
                    break;
                case '9':
                    sb.append("1001");
                    break;
                case 'a':
                    sb.append("1010");
                    break;
                case 'b':
                    sb.append("1011");
                    break;
                case 'c':
                    sb.append("1100");
                    break;
                case 'd':
                    sb.append("1101");
                    break;
                case 'e':
                    sb.append("1110");
                    break;
                case 'f':
                    sb.append("1111");
                    break;


            }
        }
        sb.append("0", 0, neededLength - sb.length());
        return sb.toString();
    }

    private String getHeaderBinaryString(IPv6Header header) {
        return getFixedBinaryString(header.getVersion(), 4) +
                getFixedBinaryString(header.getTrafficClass(), 8) +
                getFixedBinaryString(header.getFlowLabel(), 20) +
                getFixedBinaryString((long) header.getPayloadLength(), 16) +
                getFixedBinaryString((long) header.getNextHeader().getHeaderType().getCode(), 8) +
                getFixedBinaryString((long) header.getHopLimit(), 8) +
                header.getSourceAddress().getBinaryAddress() +
                header.getDestinationAddress().getBinaryAddress();
    }
}
