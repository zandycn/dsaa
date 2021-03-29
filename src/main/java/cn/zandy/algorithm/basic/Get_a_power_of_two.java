package cn.zandy.algorithm.basic;

/**
 * 命题：查找比给定整数大的第一个 2^n
 * 方案：用二进制思维解决
 * <p>
 * 由于二进制的特性，决定了 2^n 对应的二进制数字串只有一位是1，其它位都位0
 * 对于一个 非2^n 的正整数，寻找比其大的第一个 2^n，方法是:
 * · 1.把 该二进制数【最左侧第一次出现的1】的右面每一位都变成1
 * · 2.在得到的数字基础上加1
 * <p>
 * 比如: 数字9 (二进制 0000 1010)，寻找比其稍大的 2^n:
 * · 第一步 0000 1111
 * · 第二步 0000 1111 + 0000 0001 = 0001 0000  十进制为16
 */
public class Get_a_power_of_two {

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static final boolean DEBUG = false;

    /**
     * 指定一个数 number, 获取比它大的最小的二次幂数.
     *
     * @param number 指定整数
     * @param strict true: 当 number 是二的整数次幂时，返回下一整数次幂；false: 当 number 是二的整数次幂时，返回 number
     */
    public static int get(int number, boolean strict) {
        printf("number", number);

        /* "number - 1" 是为了实现当 number 是二的整数次幂时，通过该方法获取到的是当前值(2^n)，而不是下一项(2^n * 2)) */
        int n = strict ? number : number - 1;
        printf("strict(" + strict + "), n", n);

        n |= n >>> 1; // 第一次 n|=n>>>1 之后，n 的高位前(2)位都变为1
        printf("(n |= n >>> 1)", n);
        n |= n >>> 2; // 再进行 n|=n>>>2 之后，n 的高位前(4)位都变为1，以此类推 n|=n>>>4,n|=n>>>8,n|=n>>>16,n|=n>>>32 ……
        printf("(n |= n >>> 2)", n);
        n |= n >>> 4;
        printf("(n |= n >>> 4)", n);
        n |= n >>> 8;
        printf("(n |= n >>> 8)", n);
        n |= n >>> 16; // 共移动32位，因为 int 4 字节共32位
        printf("(n |= n >>> 16)", n);

        // return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;

        /* -- 分析返回值 --------------------------*/
        if (n < 0) {
            // -- 负数都返回 1 ------------ //
            println("n < 0");
            return 1;
        }

        if (n >= MAXIMUM_CAPACITY) {
            // -- 大于 MAXIMUM_CAPACITY 的都返回 MAXIMUM_CAPACITY ------------ //
            println("n >= " + MAXIMUM_CAPACITY);
            return MAXIMUM_CAPACITY;
        } else {
            // -- 所有低位变成1后，加1即可 ------------ //
            println("0 <= n < " + MAXIMUM_CAPACITY);
            return n + 1;
        }
    }

    public static void main(String[] args) {
        // 测试负整数
        //loop(-10, -8, true);
        //loop(-10, -8, false);

        // 测试大于 MAXIMUM_CAPACITY 的整数
        //loop(0B0100_0000_0000_0000_0000_0000_0000_0001, 0B0100_0000_0000_0000_0000_0000_0000_0100, true);
        //loop(0B0100_0000_0000_0000_0000_0000_0000_0001, 0B0100_0000_0000_0000_0000_0000_0000_0100, false);

        // 测试小于 MAXIMUM_CAPACITY 的自然数
        loop(0, 5, true);
        loop(0, 5, false);

        // 测试 "int n = number - 1" 和 "不减1" 的差异
        //for (int i = 1; i <= 8; i *= 2) {
        //    System.out.println("get(" + i + ")=" + get(i, true));
        //    System.out.println("get(" + i + ")=" + get(i, false));
        //    System.out.println("--------------------------");
        //}
    }

    private static void loop(int min, int max, boolean b) {
        for (int i = min; i <= max; i++) {
            System.out.println("get(" + i + ")=" + get(i, b));
            System.out.println("--------------------------");
        }
    }

    private static void printf(String k, int n) {
        if (DEBUG) {
            System.out.printf("%-16s = %-14s = %s(binary)\n", k, n + "(decimal)", Integer.toBinaryString(n));
        }
    }

    private static void println(String msg) {
        if (DEBUG) {
            System.out.println(msg);
        }
    }
}
