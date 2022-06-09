import com.hades.encap.utils.CommonUtils;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class CommonTest {
    /**
     * 测试获取MAC地址方法
     *
     * @author Kwanho
     */
    @Test
    public void test() {
        System.out.println(CommonUtils.getMacAddressLinux());
    }

    /**
     * 输入字符串，输出长度
     *
     * @author Kwanho
     */
    public static void main(String[] args) {
        // 控制台输入
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(s.length());
    }

    @Test
    public void testDomain() {
        String domain = "www.baidu.com";
        System.out.println(CommonUtils.domainToIp(domain));
    }
}
