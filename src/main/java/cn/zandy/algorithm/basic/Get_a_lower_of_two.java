package cn.zandy.algorithm.basic;

/**
 * 查找比给定正整数n(n>1)小的第一个2的N次幂.
 */
public class Get_a_lower_of_two {

    public static int get(int number) {
        if (number <= 1) {
            throw new IllegalArgumentException("无效的整数[" + number + "]");
        }

        int numberRightOne = number & ((~number) + 1);

        if (number == numberRightOne) {
            return number >>>= 1;
        }

        number >>>= 1;
        number |= number >>> 1;
        number |= number >>> 2;
        number |= number >>> 4;
        number |= number >>> 8;
        number |= number >>> 16;
        number += 1;

        return number;
    }

    public static void main(String[] args) {
        for (int i = 2; i <= 100; i++) {
            System.out.println("get(" + i + ")=" + get(i));
            System.out.println("--------------------------");
        }
    }
}
