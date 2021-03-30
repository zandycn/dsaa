package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;
import cn.zandy.algorithm.util.ParamCheckUtils;

/**
 * 数组小和问题：
 *
 * 在数组中，求一个数【左边】比它小的数之和，得到【这个数的小和】；
 * 数组中所有数的小和累加起来，叫【数组小和】。
 *
 * 求指定数组的小和。
 *
 * 比如有数组：[2,3,6,5,1,4]
 * 2的小和为：0
 * 3的小和为：2
 * 6的小和为：2+3=5
 * 5的小和为：2+3=5
 * 1的小和为：0
 * 4的小和为：2+3+1=6
 * 所以数组的小和为：2+5+5+6=18
 */
public class Code_02_UsingMergeSort_MSum {

    private static final boolean DEBUG = false;

    private static long c = 0;

    /**
     * 借助【归并排序】求小和.
     */
    private static int getMSum(int[] arr) {
        return recursion(arr, 0, arr.length - 1);
    }

    private static int recursion(int[] arr, int l, int r) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        if (arr.length == 1) {
            return 0;
        }

        ParamCheckUtils.checkLR(arr.length, l, r);

        if (l == r) {
            return 0;
        }

        int mid = l + ((r - l) >> 1);

        return recursion(arr, l, mid) + recursion(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {
        if (DEBUG) {
            System.out.println("\n--------begin merge--------");
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
            System.out.println("l = " + l);
            System.out.println("mid = " + mid);
            System.out.println("r = " + r);
            System.out.println("");
        }

        int li = l;       // 左侧区域开始位置的"指针"
        int ri = mid + 1; // 右侧区域开始位置的"指针"

        int[] tmpArr = new int[r - l + 1];
        int ti = 0; // tmpArr要存放的位置"指针"

        int tmpSum = 0;

        /* ①当左右两侧都没越界时，比较左侧右侧"指针"指向位置的值大小，把小值放入tmpArr，并向右移动小值所在一侧的"指针"。
           注：小和问题，当值相等时必须先取右侧） */
        while (li <= mid && ri <= r) {
            if (arr[li] < arr[ri]) {
                tmpArr[ti] = arr[li];
                tmpSum += arr[li] * (r - ri + 1); // 根据右边已排好序的特性，可知右边有多少个数比 arr[li] 大
                li++;
            } else {
                tmpArr[ti] = arr[ri];
                ri++;
            }
            ti++;
        }

        /* ②走到这里，表示有一测越界了（注：不可能同时越界！！因为上面的循环中，每次要么给li++, 要么给ri++ */
        // 右侧越界，把左侧剩余的复制进去
        // 左侧剩余的这几个，不小于右侧任何一个数，所以这里不需要再考虑小和
        while (li <= mid) {
            tmpArr[ti++] = arr[li++];
        }

        // 左侧越界，把右侧剩余的复制进去
        // 右侧剩余的这几个，已经在第一个 while 里计算过小和了，所以这里不需要再考虑小和
        while (ri <= r) {
            tmpArr[ti++] = arr[ri++];
        }

        /* ③走到这里，表示 l->r 范围内已经排序完成，将结果刷回原数组中 */
        for (int i = 0; i < tmpArr.length; i++) {
            arr[l + i] = tmpArr[i];
        }

        if (DEBUG) {
            System.out.println("--------after merge--------");
            System.out.println(ArrayUtils.toFormattedString(tmpArr, 0, tmpArr.length - 1));
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
            System.out.println("第" + (++c) + "次递归，范围 [" + l + ", " + r + "](" + mid + ") 最小和: tmpSum = " + tmpSum);
            System.out.println("====================================================");
        }

        return tmpSum;
    }

    /**
     * 为了对比数据实现的方法.
     */
    private static int getMSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        if (arr.length == 1) {
            return 0;
        }

        int sum = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    sum += arr[j];
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {2, 3, 6, 5, 1, 4};
        System.out.println(getMSum1(arr));
        System.out.println(getMSum(arr));
        System.out.println("====================================================");

        CompareUtils.compareIntResult(500000, 200, 100, (a) -> getMSum1(a), (a) -> getMSum(a));
    }
}
