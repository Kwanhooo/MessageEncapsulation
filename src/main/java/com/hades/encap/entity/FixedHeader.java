package com.hades.encap.entity;

import com.hades.encap.constant.IPv6HeaderTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * IPv6报头类
 *
 * @author Kwanho
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FixedHeader {
    private long version;
    private long trafficClass;
    private long flowLabel;
    private int payloadLength;
    private String nextHeader;
    private int hopLimit;
    private IPAddress sourceAddress;
    private IPAddress destinationAddress;

    /**
     * 报头类型
     */
    private IPv6HeaderTypes headerType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FixedHeader that = (FixedHeader) o;
        return Objects.equals(version, that.version) && Objects.equals(trafficClass, that.trafficClass) && Objects.equals(flowLabel, that.flowLabel) && Objects.equals(payloadLength, that.payloadLength) && Objects.equals(nextHeader, that.nextHeader) && Objects.equals(hopLimit, that.hopLimit) && Objects.equals(sourceAddress, that.sourceAddress) && Objects.equals(destinationAddress, that.destinationAddress) && headerType == that.headerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, trafficClass, flowLabel, payloadLength, nextHeader, hopLimit, sourceAddress, destinationAddress, headerType);
    }
}
