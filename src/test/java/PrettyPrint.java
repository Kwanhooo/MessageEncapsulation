import java.util.Scanner;

public class PrettyPrint {
    /**
     * 优化打印一串二进制数
     *
     * @param data 二进制数
     * @author Kwanho
     */
    public static void prettyPrint(String data) {
        int count = 0;
        for (int i = 0; i < data.length(); i++) {
            System.out.print(data.charAt(i));
            count++;
            if (count % 2 == 0) {
                System.out.print(" ");
            }
            if (count % 16 == 0) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        prettyPrint(scanner.next());
    }
}
