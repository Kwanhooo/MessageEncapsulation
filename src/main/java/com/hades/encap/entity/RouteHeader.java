package com.hades.encap.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RouteHeader extends ExtendedHeader {
    private String headerExtensionLength;
    private String routeType;
    private String leftSection;
    private String options;
    private String fill;


    public RouteHeader() {
        this.generateRouteType();
        this.generateLeftSection();
        this.generateOptions();
        this.generateFill();
        this.generateHELength();
    }

    public void generateNextHeader() {
        this.setNextHeader("00101100");
    }

    public void generateRouteType() {
        int r = (int) (Math.random() * 7);
        switch (r) {
            case 0 -> this.setRouteType("00000000");
            case 1 -> this.setRouteType("00000001");
            case 2 -> this.setRouteType("00000010");
            case 3 -> this.setRouteType("00000011");
            case 4 -> this.setRouteType("00000100");
            case 5 -> this.setRouteType("11111101");
            case 6 -> this.setRouteType("11111110");
        }
    }

    public void generateLeftSection() {
        int x = (int) (Math.random() * 256);
        StringBuilder s = new StringBuilder(Integer.toBinaryString(x));
        int length = 8 - s.length();
        for (int i = 0; i <= length - 1; i++)
            s.insert(0, "0");
        this.setLeftSection(s.toString());
    }


    public void generateOptions() {
        this.setOptions("0110");
    }

    public void generateFill() {
        int length = this.getOptions().length() % 8;
        this.setFill("0".repeat(length));
    }

    public void generateHELength() {
        int x = 24 + options.length() + fill.length();
        StringBuilder s = new StringBuilder(Integer.toBinaryString(x));
        int length = 8 - s.length();
        for (int i = 0; i <= length - 1; i++)
            s.insert(0, "0");
        this.setHeaderExtensionLength(s.toString());
    }

    @Override
    public String toString() {
        return nextHeader +
                headerExtensionLength +
                routeType +
                leftSection +
                options +
                fill;
    }
}
