package cn.zandy.algorithm.basic.c01;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

/**
 * 插入排序.
 *
 * 给定数组 由小到大 排序：
 *
 * 思路————从左到右依次有序
 * · 让 arr[0~1] 上有序，所以从 arr[1] 开始往前看，如果 arr[1] < arr[0]，就交换，否则什么也不做；
 * · 让 arr[0~2] 上有序，所以从 arr[2] 开始往前看，如果 arr[2] < arr[1]，就交换及继续上一步，否则什么也不做；
 * · …
 * · 让 arr[0~i] 上有序，所以从 arr[i] 开始往前看，arr[i] 这个数不停向左移动，一直移动到左边的数字不再比自己大，停止移动；
 * · 最后一步，让 arr[0~N-1] 上有序，arr[N-1] 这个数不停向左移动，一直移动到左边的数字不再比自己大，停止移动。
 */
public class Code_03_InsertionSort {

    private void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int len = arr.length;

        // 0 ~ 1, 2, 3, 4, 5, 6, …… N-1 上有序
        for (int i = 1; i < len; i++) {
            // 一直移动到左边的数字不再比自己大
            for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
                ArrayUtils.swap(arr, j - 1, j);
            }
        }
    }

    /**
     * 使用对数器测试.
     */
    public static void main(String[] args) {
        CompareUtils.compareSortResultWithJDK(new Code_03_InsertionSort()::sort);
    }
}
