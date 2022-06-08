package com.hades.encap.entity;

import com.hades.encap.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static com.hades.encap.utils.CommonUtils.longToBinaryString;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationHeader extends ExtendedHeader {
    private int payloadLength;//8
    private int reserved;//16
    private String SPI;//32
    private String sequenceNumber;//32
    private String ICV;//32的倍数

    public AuthenticationHeader() {
        this.nextHeader = null;
        this.reserved = 0;
        this.SPI = CommonUtils.generateRandomBinaryString(32);
        this.sequenceNumber = CommonUtils.generateRandomBinaryString(32);
        // 随机生成一个1到10的整数
        int times = (int) (Math.random() * 10 + 1);
        this.ICV = CommonUtils.generateRandomBinaryString(32 * times);
        this.payloadLength = (8 + 8 + 16 + 32 + 32 + 32 * times) / 32 - 2;
    }

    @Override
    public String toString() {
        // 断言nextHeader不为空，否则报错
        assert nextHeader != null : "AuthenticationHeader::nextHeader is null";
        return nextHeader +
                longToBinaryString(payloadLength, 8) +
                longToBinaryString(reserved, 16) +
                SPI +
                sequenceNumber +
                ICV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationHeader that = (AuthenticationHeader) o;
        return payloadLength == that.payloadLength && reserved == that.reserved && Objects.equals(SPI, that.SPI) && Objects.equals(sequenceNumber, that.sequenceNumber) && Objects.equals(ICV, that.ICV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payloadLength, reserved, SPI, sequenceNumber, ICV);
    }
}
