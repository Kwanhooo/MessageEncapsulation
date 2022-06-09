package com.hades.encap.controller;

import com.hades.encap.constant.IPv6HeaderTypes;
import com.hades.encap.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.hades.encap.controller.EncapsulationHandler.getFixedBinaryString;
import static com.hades.encap.utils.CommonUtils.prettyPrint;

@Slf4j
public class Application {
    public static String srcIP;
    public static String dstIP;


    /**
     * 全局入口
     *
     * @param args 命令行参数
     * @apiNote 通过附加命令行参数 程序名 TCP数据(文件位置) IPv6数据(文件位置) 的方式来启动
     * @author Kwanho
     */
    public static void main(String[] args) {
//        new Thread(CommonUtils::bugsGoAway).start();

        // 命令行参数检查
        assert args.length == 2 : "命令行参数有误！";

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要请求的地址：(输入#随机生成)");
        String domain = scanner.next();
        if (domain.equals("#")) {
            System.out.println("您没有输入请求地址，随机生成一个目的地址...");
            srcIP = IpUtil.getRandomIp();
            dstIP = IpUtil.getRandomIp();
        } else {
            srcIP = IpUtil.getRandomIp();
            dstIP = IpUtil.domainToIp(domain);
        }


        // 获得参数
        String tcpDataFile = args[0].trim();
        String ipv6DataFile = args[1].trim();
        log.info("tcpDataFile: {}", tcpDataFile);
        log.info("ipv6DataFile: {}", ipv6DataFile);

        // 从TCP数据文件中读取数据
        File tcpFile = new File(tcpDataFile);
        String tcpData;
        try {
            tcpData = FileUtils.readFileToString(tcpFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("读取TCP数据文件失败！");
            throw new RuntimeException(e);
        }
        // 消除tcpData中的空格和换行
        tcpData = tcpData.replaceAll("\\s", "");
        log.info("TCP Data: {}", tcpData);

        // 转到主处理器
        EncapsulationHandler handler = new EncapsulationHandler();
        String ipv6Data = handler.handle(tcpData);

        // 输出到IPv6数据文件
        String prettyIPv6Data = prettyPrint(ipv6Data);
        File ipv6File = new File(ipv6DataFile);
        try {
            FileUtils.writeStringToFile(ipv6File, prettyIPv6Data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("写入IPv6数据文件失败！");
            throw new RuntimeException(e);
        }
        log.info("已写入IPv6数据文件：{}", ipv6File.getAbsoluteFile());

        // 以下生成伪报头
        String fakeSrcIp = getBinaryAddress(srcIP);
        String fakeDstIp = getBinaryAddress(dstIP);
        String nextHead = getFixedBinaryString((long) IPv6HeaderTypes.TCP.getCode(), 8);
        String zero = "0".repeat(24);
        String length = getFixedBinaryString((long) (tcpData.length() + 40 * 32), 32);

        String fakeAll = fakeSrcIp + fakeDstIp + zero + length + nextHead;

        System.out.println();
        System.out.println("<==          伪报头          ==>");
        for (int i = 0; i < fakeAll.length(); i++) {
            System.out.print(fakeAll.charAt(i));
            if ((i + 1) % 32 == 0) {
                System.out.println();
            }
        }
        System.out.println("<==       共 " + fakeAll.length() + " bits       ==>");
    }

    public static String getBinaryAddress(String ip) {
        String[] ipArray = ip.split(":");
        StringBuilder sb = new StringBuilder();
        for (String i : ipArray) {
            if (i.equals(""))
                i = "0";
            sb.append(getFixedBinaryString(Long.parseLong(i, 16), 16));
        }
        return sb.toString();
    }
}
