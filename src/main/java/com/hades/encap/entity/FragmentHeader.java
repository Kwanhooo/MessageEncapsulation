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
public class FragmentHeader extends ExtendedHeader {
    private int nextHeader;
    private int reserved;
    private int fragmentOffset;
    private int moreFragment;
    private int identification;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FragmentHeader that = (FragmentHeader) o;
        return nextHeader == that.nextHeader && reserved == that.reserved && fragmentOffset == that.fragmentOffset && moreFragment == that.moreFragment && identification == that.identification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nextHeader, reserved, fragmentOffset, moreFragment, identification);
    }
}
