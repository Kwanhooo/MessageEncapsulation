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

    /**
     * 玄学启动
     *
     * @author kwanho
     */
    public static void bugsGoAway() {
        System.out.println(" .................IPv6报文封装和地址生成..............");
        System.out.println(" ................Powered by Hades Group.............");
        System.out.println(" ...................................................");
        System.out.println(" ......................我佛慈悲......................");
        System.out.println("                       _oo0oo_                      ");
        System.out.println("                      o8888888o                     ");
        System.out.println("                      88\" . \"88                     ");
        System.out.println("                      (| -_- |)                     ");
        System.out.println("                      0\\  =  /0                     ");
        System.out.println("                    ___/‘---’\\___                   ");
        System.out.println("                  .' \\|       |/ '.                 ");
        System.out.println("                 / \\\\|||  :  |||// \\                ");
        System.out.println("                / _||||| -卍-|||||_ \\               ");
        System.out.println("               |   | \\\\\\  -  /// |   |              ");
        System.out.println("               | \\_|  ''\\---/''  |_/ |              ");
        System.out.println("               \\  .-\\__  '-'  ___/-. /              ");
        System.out.println("             ___'. .'  /--.--\\  '. .'___            ");
        System.out.println("          .\"\" ‘<  ‘.___\\_<|>_/___.’ >’ \"\".          ");
        System.out.println("         | | :  ‘- \\‘.;‘\\ _ /’;.’/ - ’ : | |        ");
        System.out.println("         \\  \\ ‘_.   \\_ __\\ /__ _/   .-’ /  /        ");
        System.out.println("     =====‘-.____‘.___ \\_____/___.-’___.-’=====     ");
        System.out.println("                       ‘=---=’                      ");
        System.out.println("                                                    ");
        System.out.println("....................佛祖开光，永无BUG...................");
        System.out.println();
    }
}
