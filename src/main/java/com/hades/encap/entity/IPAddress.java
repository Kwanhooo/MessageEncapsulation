package com.hades.encap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IPAddress {
    private long prefixSubnetID;
    private String interfaceID;

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
