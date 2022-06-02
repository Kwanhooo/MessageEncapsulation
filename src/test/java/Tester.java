import com.hades.encap.utils.CommonUtils;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class Tester {
    @Test
    public void test() {
        System.out.println(CommonUtils.getMacAddressLinux());
    }

    public static void main(String[] args) {
        // 控制台输入
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(s.length());
    }
}
