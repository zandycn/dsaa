package cn.zandy.algorithm.basic.c02;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;
import cn.zandy.algorithm.util.ParamCheckUtils;

/**
 * 求给定数组最大元素.
 */
public class Code_07_Recursion {

    private static int getMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        return rfunc(arr, 0, arr.length - 1);
    }

    private static int rfunc(int[] arr, int l, int r) {
        ParamCheckUtils.checkLR(arr.length, l, r);

        // base case
        if (l == r) {
            return arr[l];
        }

        int mid = l + ((r - l) >> 1);

        int a = rfunc(arr, l, mid);
        int b = rfunc(arr, mid + 1, r);

        return a > b ? a : b;
    }

    private static int getMax1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[] arr = ArrayUtils.generateRandomArray(10, 100);

        int r1 = getMax(arr);
        int r2 = getMax1(arr);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r1 == r2);

        System.out.println("==========================");

        CompareUtils.compareIntResult(500000, 200, 200, Code_07_Recursion::getMax, Code_07_Recursion::getMax1);
    }
}
