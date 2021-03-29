package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.basic.Get_a_lower_of_two;
import cn.zandy.algorithm.basic.Get_a_power_of_two;
import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

/**
 * 归并排序.
 *
 * 给定数组 由小到大 排序：
 *
 * 这里采用【递归】和【非递归】两种方式实现
 */
public class Code_01_MergeSort {

    private static final boolean DEBUG = false;

    private void recurSort(int[] arr) {
        recursion(arr, 0, arr.length - 1);
    }

    /**
     * 【递归实现】
     * ①左侧排好序，②右侧排好序，③merge两侧让整体有序。
     * · 比如：[3,1,2,4]，左[3,1]右[2,4] ①[1,3] ②[2,4] ③[1, 2, 3, 4]
     * · 注意：不熟练时，先画递归脑图来观察，再实现代码
     *
     * 递归函数定义————让数组 arr 在下标 l->r 范围上有序（由小到大）
     *
     * 时间复杂度：符合Master公式前提，T(N)=2*T(N/2) + O(N) 所以是 O(N*logN)
     */
    private void recursion(int[] arr, int l, int r) {
        if (arr == null || arr.length < 2) {
            return;
        }

        if (l > r || l < 0 || r > arr.length - 1) {
            throw new IllegalArgumentException("传入位置参数错误");
        }

        // "范围"只对应数组中一个元素，那一定有序，不需要再拆分，直接返回（这种情况就是 Base Case）
        if (l == r) {
            return;
        }

        int mid = l + ((r - l) >> 1);
        recursion(arr, l, mid);
        recursion(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    /**
     * 调用 merge 时必须保证左右两侧是有序的，否则 merge 完不能保证有序！！
     *
     * 2N, 去掉常数项时间复杂度为 O(N), 注：其中样本数量N 是 r-l+1
     */
    private void merge(int[] arr, int l, int mid, int r) {
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

        /* ①当左右两侧都没越界时，比较左侧右侧"指针"指向位置的值大小，把小值放入tmpArr，并向右移动小值所在一侧的"指针"。注：当值相等时我们取了左侧） */
        while (li <= mid && ri <= r) {
            if (arr[li] <= arr[ri]) {
                tmpArr[ti] = arr[li];
                li++;
            } else {
                tmpArr[ti] = arr[ri];
                ri++;
            }
            ti++;
        }

        /* ②走到这里，表示有一测越界了（注：不可能同时越界！！因为上面的循环中，每次要么给li++, 要么给ri++ */
        // 右侧越界，把左侧剩余的复制进去
        while (li <= mid) {
            tmpArr[ti++] = arr[li++];
        }
        // 左侧越界，把右侧剩余的复制进去
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
        }
    }

    /**
     * 【非递归实现】
     * 每相邻2个元素为一组，调用merge将每组排好序；
     * 每相邻4个元素为一组，调用merge将每组排好序；
     * 每相邻8个元素为一组，调用merge将每组排好序；
     * 每相邻16个元素为一组，调用merge将每组排好序；
     * ...
     * 最后，每相邻2^N个元素为一组(2^N大于等于数组长度)，调用merge将整体排好序。
     */
    private void iterativeSortByLower(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;

        // 计算最大的分组大小，其特征：
        // · maxGroupSize 是二的整数次幂
        // · maxGroupSize < arr.length 且 maxGroupSize*2 >= arr.length
        int maxGroupSize = Get_a_lower_of_two.get(len);

        /* ① 先保证 0->(maxGroupSize-1) 有序，maxGroupSize->arr.length-1 有序 */
        int l, mid, r;
        for (int groupSize = 2; groupSize <= maxGroupSize; groupSize <<= 1) {
            for (l = 0; l < arr.length; l += groupSize) {
                r = Math.min(l + groupSize - 1, arr.length - 1);
                mid = l + ((groupSize - 1) >> 1);
                if (mid > r) {
                    mid = l + ((r - l) >> 1);
                }
                merge(arr, l, mid, r);
            }
        }

        /* ② 最后保证整体有序 */
        merge(arr, 0, maxGroupSize - 1, len - 1);
    }

    private void iterativeSortByPower(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;
        int maxGroupSize = Get_a_power_of_two.get(len, false);

        int l, mid, r;
        for (int groupSize = 2; groupSize <= maxGroupSize; groupSize <<= 1) {
            for (l = 0; l < arr.length; l += groupSize) {
                r = Math.min(l + groupSize - 1, arr.length - 1);
                mid = l + ((groupSize - 1) >> 1);
                if (mid > r) {
                    mid = l + ((r - l) >> 1);
                }
                merge(arr, l, mid, r);
            }
        }

        // 当数组长度 len 大于 1 << 30 时, 会出现这种情况
        if (maxGroupSize < len) {
            merge(arr, 0, maxGroupSize - 1, len - 1);
        }
    }

    public static void main(String[] args) {
        //CompareUtils.compareSortResultWithJDK(new Code_01_MergeSort()::iterativeSortByLower, 1, 10, 100);
        //CompareUtils.compareSortResultWithJDK(new Code_01_MergeSort()::iterativeSortByPower, 1, 10, 100);
        CompareUtils.compareSortResultWithJDK(new Code_01_MergeSort()::recurSort);
        System.out.println("====================================================");
        CompareUtils.compareSortResultWithJDK(new Code_01_MergeSort()::iterativeSortByLower);
        System.out.println("====================================================");
        CompareUtils.compareSortResultWithJDK(new Code_01_MergeSort()::iterativeSortByPower);

        //System.out.println("Int_MAX_VALUE=" + Integer.MAX_VALUE + ", " + Integer.toBinaryString(Integer.MAX_VALUE));
        //System.out.println("MAXIMUM_CAPACITY =" + (1 << 30) + ", " + Integer.toBinaryString(1 << 30));
    }
}
