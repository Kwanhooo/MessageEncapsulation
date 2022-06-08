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
public class AuthenticationHeader extends ExtendedHeader {
//    private int nextHeader;
    private int payloadLength;
    private int reserved;
    private int SPI;
    private int sequenceNumber;
    private int ICV;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationHeader that = (AuthenticationHeader) o;
        return nextHeader == that.nextHeader && payloadLength == that.payloadLength && reserved == that.reserved && SPI == that.SPI && sequenceNumber == that.sequenceNumber && ICV == that.ICV;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextHeader, payloadLength, reserved, SPI, sequenceNumber, ICV);
    }
}
