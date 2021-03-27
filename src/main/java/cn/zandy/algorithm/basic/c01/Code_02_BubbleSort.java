package cn.zandy.algorithm.basic.c01;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

/**
 * 冒泡排序.
 *
 * 给定数组 由小到大 排序：
 *
 * 确定下标 N-1 是最大值，在 arr[0～N-1] 范围上，
 * · arr[0] 和 arr[1]，谁大谁来到 index=1 位置；
 * · arr[1] 和 arr[2]，谁大谁来到 index=2 位置；
 * · …
 * · arr[N-2] 和 arr[N-1]，谁大谁来到 index=N-1 位置；
 * 确定下标 N-2 是最大值，在 arr[0～N-2] 范围上，重复上面的过程，但最后一步是 arr[N-3] 和 arr[N-2]，谁大谁来到 index=N-2 位置
 * 确定下标 N-3 是最大值，在 arr[0～N-3] 范围上，重复上面的过程，但最后一步是 arr[N-4] 和 arr[N-3]，谁大谁来到 index=N-3 位置
 * …
 * 确定下标 1 是最大值，最后在 arr[0～1] 范围上，重复上面的过程，但最后一步是 arr[0] 和 arr[1]，谁大谁来到 index=1 位置
 */
public class Code_02_BubbleSort {

    private void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;

        // N-1, N-2, …… 1
        for (int index = len - 1; index > 0; index--) {

            // 0～N-1 两两比较，大的换到右边
            // 0～N-2 两两比较，大的换到右边
            // …
            // 0～1 两两比较，大的换到右边
            for (int i = 0; i < index; i++) {
                if (arr[i] > arr[i + 1]) {
                    ArrayUtils.swap(arr, i, i + 1);
                    //ArrayUtils.swap1(arr, i, i + 1);
                }
            }
        }
    }

    /**
     * 使用对数器测试.
     */
    public static void main(String[] args) {
        CompareUtils.compareSortResultWithJDK(new Code_02_BubbleSort()::sort);
    }
}
