package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.util.CompareUtils;

/**
 * 【快排】第三个版本——随机快排，基于 随机数 + 荷兰国旗.
 */
public class Code_05_QuickSortV3 {

    private void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        new Code_05_QuickSortV2().recursion(arr, 0, arr.length - 1, true);
    }

    /**
     * 使用对数器测试.
     */
    public static void main(String[] args) {
        //CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV3()::sort, 1, 10, 1);
        //CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV3()::sort, 1, 10, 100);
        CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV3()::sort);
    }
}
