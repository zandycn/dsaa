package cn.zandy.algorithm.basic;

/**
 * 异或运算（无进位相加）的性质：
 * (1) 0^N == N    N^N == 0
 * (2) 异或运算满足交换律和结合率
 *
 * 思考以下题目
 * 题目一：如何不用额外变量交换两个数
 * 题目二：一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 * 题目三：一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 * 题目四：找出一个整数对应二进制数字，出现1的次数
 */
public class B09_ExclusiveOR {

    public static void main(String[] args) {
        System.out.println("------ p1 ------");
        doProblem1(3, 6);
        int[] arr = {1, 2, 6};
        doProblem1(arr[2], arr[2]);
        doProblem1(arr);

        System.out.println("------ p2 ------");
        int[] arr2 = {1, 1, 2, 3, 3, 2, 1, 4, 1, 4, 5, 6, 6, 5, 9}; // 9
        doProblem2(arr2);

        System.out.println("------ p3 ------");
        int[] arr3 = {1, 1, 2, 3, 3, 2, 1, 4, 1, 4, 5, 6, 6, 5, 9, 8, 8, 8}; // 9,8
        doProblem3(arr3);

        System.out.println("------ p4 ------");
        doProblem4(0B1000_1111_1110_1100); // 10次
    }

    /**
     * 题目一.
     */
    private static void doProblem1(int a, int b) {
        System.out.println("input      : a=" + a + ", b=" + b);
        a = a ^ b;
        b = a ^ b; // 等式右侧代入a:    (a^b) ^ b = a ^ (b^b) = a ^ 0 = a
        a = a ^ b; // 等式右侧代入a和b:  (a^b) ^ a = b
        System.out.println("after swap : a=" + a + ", b=" + b);
    }

    /**
     * 题目一.
     */
    private static void doProblem1(int[] arr) {
        System.out.println("input      : a=" + arr[0] + ", b=" + arr[1]);
        arr[0] = arr[0] ^ arr[1];
        arr[1] = arr[0] ^ arr[1];
        arr[0] = arr[0] ^ arr[1];
        System.out.println("after swap : a=" + arr[0] + ", b=" + arr[1]);

        System.out.println("input      : a=" + arr[0] + ", a=" + arr[0]);
        arr[0] = arr[0] ^ arr[0];
        arr[0] = arr[0] ^ arr[0];
        arr[0] = arr[0] ^ arr[0];
        System.out.println("after swap : a=" + arr[0] + ", a=" + arr[0]);
    }

    /**
     * 题目二: 将数组中的每一个元素异或.
     */
    private static void doProblem2(int[] arr) {
        int eor = arr[0];
        for (int i = 1; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println("odd number:" + eor);
    }

    /**
     * 题目三.
     */
    private static int doProblem3(int[] arr) {
        // 1.将数组中的每一个元素异或，得到的结果就是那两种出现了奇数次的数字的异或值
        int eor = arr[0];
        for (int i = 1; i < arr.length; i++) {
            eor ^= arr[i];
        }

        // 2.由于是两种数，它们异或值(也就是变量 eor)中一定含有为1的位，所以先找到最右边是 1 的位
        int rigthOne = eor & ((~eor) + 1);

        /*
           3.假设我们找到的 rigthOne = 10000, 右数第 5 位是最右边的 1
             那么我们会知道 其中一种奇数的右数第 5 位是 1, 另一种奇数的右数第 5 位不是 1
             这时我们将数组中的元素也分成两种：右数第 5 位是 1 的元素集合，右数第 5 位不是 1 的元素集合

             分析到这里，我们就可以根据上一题的思路，找出来第一个奇数
         */

        int eorAll1 = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rigthOne) == rigthOne) { // 找到右数第 5 位是 1 的元素集合
                eorAll1 ^= arr[i];
            }
        }

        System.out.println("一种出现了奇数次的数字：" + eorAll1);
        System.out.println("另一种出现了奇数次的数字：" + (eor ^ eorAll1)); // a^b=c, 那么 b=a^b^a=c^a

        // ------------

        int eorAll0 = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rigthOne) == 0) { // 找到右数第 5 位是 0 的元素集合
                eorAll0 ^= arr[i];
            }
        }
        System.out.println("一种出现了奇数次的数字：" + eorAll0);
        System.out.println("另一种出现了奇数次的数字：" + (eor ^ eorAll0));

        return eor;
    }

    private static void doProblem4(int n) {
        System.out.println("I: " + Integer.toBinaryString(n));
        int count = 0;
        int rightOne;
        while ((rightOne = (n & ((~n) + 1))) != 0) {
            n ^= rightOne;
            count++;
        }

        System.out.println("R: " + "出现 1 的次数是：" + count);
    }
}
