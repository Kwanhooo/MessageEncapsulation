package com.hades.encap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static com.hades.encap.controller.EncapsulationHandler.getFixedBinaryString;

/**
 * IPv6地址类
 *
 * @author Kwanho
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IPAddress {
    public IPAddress(String ip) {
        this.ip = ip;
    }

    private long prefixSubnetID;
    private String interfaceID;
    private String ip;

    public String getFullyAddress() {
        return this.ip;
    }

    public String getBinaryAddress() {
        String[] ipArray = this.ip.split(":");
        StringBuilder sb = new StringBuilder();
        for (String ip : ipArray) {
            if (ip.equals(""))
                ip = "0";
            sb.append(getFixedBinaryString(Long.parseLong(ip, 16), 16));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPAddress ipAddress = (IPAddress) o;
        return prefixSubnetID == ipAddress.prefixSubnetID && Objects.equals(interfaceID, ipAddress.interfaceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefixSubnetID, interfaceID);
    }
}
