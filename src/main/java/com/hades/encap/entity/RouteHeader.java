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


    private String id = "00001111";
    private String context;
    public RouteHeader() {
      //  this.generateNextHeader();
        this.generateRouteType();
        this.generateLeftSection();
        this.generateOptions();
        this.generateFill();
        this.generateHELength();
    }

    public void generateNextHeader() {
        this.setNextHeader("00101100");
    }

    ;

    public void generateRouteType() {
        int r = (int) (Math.random() * 7);
        switch (r) {
            case 0:
                this.setRouteType("00000000");break;
            case 1:
                this.setRouteType("00000001");break;
            case 2:
                this.setRouteType("00000010");break;
            case 3:
                this.setRouteType("00000011");break;
            case 4:
                this.setRouteType("00000100");break;
            case 5:
                this.setRouteType("11111101");break;
            case 6:
                this.setRouteType("11111110");break;
        }
    }

    ;

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
        StringBuilder s = new StringBuilder();
        for (int i = 0; i <= length - 1; i++)
            s.append("0");
        this.setFill(s.toString());
    }

    public void generateHELength() {
        int x = 24 + options.length() + fill.length();
        StringBuilder s = new StringBuilder(Integer.toBinaryString(x));
        int length = 8 - s.length();
        for (int i = 0; i <= length - 1; i++)
            s.insert(0, "0");
        this.setHeaderExtensionLength(s.toString());
    }
}
