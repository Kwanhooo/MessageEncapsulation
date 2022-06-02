package com.hades.encap.controller;

import com.hades.encap.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.hades.encap.utils.CommonUtils.prettyPrint;

@Slf4j
public class Application {
    /**
     * 全局入口
     *
     * @param args 命令行参数
     * @apiNote 通过附加命令行参数 程序名 TCP数据(文件位置) IPv6数据(文件位置) 的方式来启动
     * @author Kwanho
     */
    public static void main(String[] args) {
        new Thread(CommonUtils::bugsGoAway).start();

        // 命令行参数检查
        assert args.length == 2;

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
    }
}
