package cn.zandy.algorithm.basic.c01;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

import java.util.function.Supplier;

/**
 * 在一个【有序】数组中，找 <= 某个数最右侧的位置.
 *
 * 约定：
 * · 如果数组为空，返回 #{@link CompareUtils#EMPTY_FLAG}
 * · 如果传入数字比数组中所有元素都大，返回 array.length-1
 * · 如果传入数字比数组中所有元素都小，返回 #{@link CompareUtils#NOT_FOUND_FLAG}
 */
public class Code_06_BisectionFirstLTE {

    private boolean debug;

    // O(logN)
    private int bisect(int[] arr, int n) {
        if (debug) {
            System.out.println("\n\n------------ Method start ! n=" + n + "------------");
        }

        if (arr == null || arr.length == 0) {
            return CompareUtils.EMPTY_FLAG;
        }

        int l = 0;
        int r = arr.length - 1;

        int loopCount = 0;
        int targetIndex = CompareUtils.NOT_FOUND_FLAG;
        int midIndex;

        while (l <= r) {
            loopCount++;

            midIndex = l + ((r - l) >> 1);

            if (debug) {
                System.out.println("Step " + loopCount + "\n");
                System.out.println(ArrayUtils.toFormattedString(arr, l, r));
                System.out.println("");
                System.out.println("(" + l + ", " + r + ") mid_index=" + midIndex + ", lastTargetIndex=" + targetIndex);
                System.out.println("------------");
            }

            if (arr[midIndex] > n) {
                r = midIndex - 1;
            } else {
                targetIndex = midIndex;
                l = midIndex + 1;
            }
        }

        if (debug) {
            System.out.println("last: (l=" + l + ", r=" + r + ")");
            System.out.println("------------ Method end ! targetIndex=" + targetIndex + "------------");
        }

        return targetIndex;
    }

    // O(N)
    private int iterate(int[] arr, int n) {
        if (arr == null || arr.length == 0) {
            return CompareUtils.EMPTY_FLAG;
        }

        int targetIndex = CompareUtils.NOT_FOUND_FLAG;

        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= n) {
                targetIndex = i;
                break;
            }
        }

        return targetIndex;
    }

    public static void main(String[] args) {
        Code_06_BisectionFirstLTE ins = new Code_06_BisectionFirstLTE();

        Supplier<Integer> keyGen = () -> (int) (Math.random() * 121 - Math.random() * 101);

        //ins.debug = true;
        //CompareUtils.compare4Bisection(ins::bisect, ins::iterate, keyGen, 100, 10, 100, ins.debug);
        CompareUtils.compare4Bisection(ins::bisect, ins::iterate, keyGen, ins.debug);
    }
}
