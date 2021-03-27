package cn.zandy.algorithm.basic;

/**
 * Created by zandy on 2021/3/4.
 */
public class BinaryCode {

    // byte 1字节 8位 范围 -2^7(128) ~ 2^7-1(127)
    // Integer 范围
    // zhuanlan.zhihu.com/p/91967268
    public static void main(String[] args) {
        int m = 0B1111_1111_1111_1111_1111_1111_1111_1111;
        int min = 0B1000_0000_0000_0000_0000_0000_0000_0000;
        int max = 0B0111_1111_1111_1111_1111_1111_1111_1111;
        System.out.println("m=" + m + ", min=" + min + ", max=" + max);
        System.out.println("max == min - 1 ? " + (max == min - 1));
        System.out.println("min == max + 1 ? " + (min == max + 1));
        System.out.println(Integer.toBinaryString(2147483647));

        System.out.println("------------");

        int i = 8;

        // 反码
        System.out.println(" i=  " + i + " : " + Integer.toBinaryString(i));
        System.out.println("~i= " + ~i + " : " + Integer.toBinaryString(~i));

        System.out.println(~i == m - i);
    }
}
