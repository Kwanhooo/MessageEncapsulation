package com.hades.encap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionsPerHopHeader extends ExtendedHeader {
  //  private String nextHeader;
    private String headerExtensionLength;
    private String options;
    private String fill;
    private String id = "00000000";

    public OptionsPerHopHeader() {
     //   this.generateNextHeader();
        this.generateOptions();
        this.generateFill();
        this.generateHELength();
    }

    public void generateNextHeader() {
        this.setNextHeader("00101011");
    }

    public void generateOptions() {
        this.setOptions("0110");
    }

    public void generateFill() {
        int length = this.getOptions().length() % 8;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i <= length - 1; i++)
            s.append("0");
        this.setFill(s.toString());
    }

    public void generateHELength() {
        int x = 8 + options.length() + fill.length();
        StringBuilder s = new StringBuilder(Integer.toBinaryString(x));
        int length = 8 - s.length();
        for (int i = 0; i <= length - 1; i++)
            s.insert(0, "0");
        this.setHeaderExtensionLength(s.toString());
    }
}
