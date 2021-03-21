package cn.zandy.algorithm.basic;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.Comparator;

/**
 * 选择排序.
 *
 * 给定数组 由小到大 排序：
 * · arr[0～N-1]   范围上，找到最小值所在的位置，然后把最小值交换到 0 位置；
 * · arr[1～N-1]   范围上，找到最小值所在的位置，然后把最小值交换到 1 位置；
 * · arr[2～N-1]   范围上，找到最小值所在的位置，然后把最小值交换到 2 位置；
 * · …
 * · arr[N-2～N-1] 范围上，找到最小值所在的位置，然后把最小值交换到 N-2 位置。
 */
public class B01_01_SelectionSort {

    private void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 最小值所在位置
        int minValueIndex = 0;

        // 0, 1, 2, 3, …… N-2
        for (int i = 0; i < arr.length - 1; i++) {
            minValueIndex = i;

            // 0～N-1 比较
            // 1～N-1 比较
            // …
            // N-2～N-1 比较
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minValueIndex] > arr[j]) {
                    minValueIndex = j;
                }
            }

            // 把最小值交换到 i 位置
            ArrayUtils.swap(arr, i, minValueIndex);
        }
    }

    /**
     * 使用对数器测试.
     */
    public static void main(String[] args) {
        Comparator.compareForSort(new B01_01_SelectionSort()::sort);
    }
}
