package com.hades.encap.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommonUtils {
    /**
     * 获取本机的MAC地址
     * Windows
     *
     * @return 本机的MAC地址
     * @author Kwanho
     */
    public static String getMacAddressWindows() {
        String macAddress = null;
        try {
            Process process = Runtime.getRuntime().exec("ipconfig /all");
            process.getOutputStream().close();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = process.getInputStream().read(bytes)) != -1) {
                String str = new String(bytes, 0, length, "gb2312");
                if (str.contains("物理地址")) {
                    int index = str.indexOf(":");
                    macAddress = str.substring(index + 1).trim();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    /**
     * 获取本机的MAC地址
     * Passed on Ubuntu 22.04 LTS
     *
     * @return 本机的MAC地址
     * @author Kwanho
     */
    public static String getMacAddressLinux() {
        String macAddress = null;
        try {
            Process process = Runtime.getRuntime().exec("ifconfig eno1");
            process.getOutputStream().close();
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("ether")) {
                    macAddress = line.substring(line.indexOf("ether") + 6, line.indexOf("ether") + 23).trim();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddress;
    }
}
