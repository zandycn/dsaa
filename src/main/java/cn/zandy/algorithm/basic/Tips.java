package cn.zandy.algorithm.basic;

/**
 * tips.
 */
public class Tips {

    public static void main(String[] args) {
        t1(126);
        t1(1_200_000_000);

        System.out.println("------------");
        t2(0B0001_0101_0100);
        System.out.println("------------");
        t2(0B0000_0100_0000);
        System.out.println("------------");
        t2(0B0000_0000_0000);
    }

    /**
     * n*2+1 等于 (n<<1)|1
     */
    private static void t1(int n) {
        int x = n * 2 + 1;
        int y = (n << 1) | 1;

        System.out.printf("x==y ? %s, x=%d y=%d%n", x == y, x, y);
    }

    /**
     * 指定一个 int 类型的二进制数，把它最右侧的 1 提取出来
     *
     * @param n n>0
     */
    private static void t2(int n) {
        int result = n & ((~n) + 1);

        System.out.println("I : " + Integer.toBinaryString(n));
        System.out.println("O : " + Integer.toBinaryString(result));
    }
}
