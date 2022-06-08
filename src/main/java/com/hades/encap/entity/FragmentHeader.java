package com.hades.encap.entity;

import com.hades.encap.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.hades.encap.utils.CommonUtils.longToBinaryString;

@Getter
@Setter
@AllArgsConstructor
public class FragmentHeader extends ExtendedHeader {
    private int reserved;//8
    private String fragmentOffset;//13
    private String res;//2
    private String moreFragment;//1
    private String identification;//32

    public FragmentHeader() {
        this.nextHeader = null;
        this.reserved = 0;
        this.fragmentOffset = CommonUtils.generateRandomBinaryString(13);
        this.res = CommonUtils.generateRandomBinaryString(2);
        this.moreFragment = CommonUtils.generateRandomBinaryString(1);
        this.identification = CommonUtils.generateRandomBinaryString(32);
    }

    @Override
    public String toString() {
        // 断言nextHeader不为空，否则报错
        assert nextHeader != null : "FragmentHeader::nextHeader is null";
        return nextHeader +
                longToBinaryString(reserved, 8) +
                fragmentOffset +
                res +
                moreFragment +
                identification;
    }
}
