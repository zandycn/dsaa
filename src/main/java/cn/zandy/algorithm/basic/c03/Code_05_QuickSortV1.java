package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

/**
 * 【快排】第一个版本，基于 partition:
 *
 * 以数组 最后一个元素 进行 partition, 然后将其和小于区下一个元素交换，
 * 再将它左右两侧递归 以同样方式进行 partition.
 *
 * 时间复杂度: O(N^2), 受随机生成的样本数据状况影响，当样本数据刚生成就有序时 复杂度最高。
 */
public class Code_05_QuickSortV1 {

    private static final boolean DEBUG = false;

    private static long c = 0;

    private Code_04_Partition ins = new Code_04_Partition();

    private void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        recursion(arr, 0, arr.length - 1);
    }

    /**
     * 递归函数定义：保证数组 l->r 范围内有序.
     */
    private void recursion(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }

        if (DEBUG) {
            System.out.println("\n----------------");
            System.out.println("第" + (++c) + "次递归【开始】，范围 [" + l + ", " + r + "]");
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
            System.out.println("");
        }

        int firstLargerIndex = ins.partition(arr, arr[r], l, r - 1);

        if (DEBUG) {
            System.out.println("firstLargerIndex=" + firstLargerIndex);
            System.out.println("partition之后的数组：");
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
        }

        /* 根据 ins.partition 方法的实现可知，firstLargerIndex 取值范围是 -1 或 [l,r-1] */
        // arr[r] 在 [l,r-1] 范围内是最大的，所以不需要交换
        if (firstLargerIndex == -1) {
            recursion(arr, l, r - 1);
        }

        // arr[r] 在 [l,r-1] 范围内是最小的，需要交换到第一个位置
        else if (firstLargerIndex == l) {
            ArrayUtils.swap(arr, l, r);
            recursion(arr, l + 1, r);
        } else {
            ArrayUtils.swap(arr, firstLargerIndex, r);
            recursion(arr, l, firstLargerIndex - 1);
            recursion(arr, firstLargerIndex + 1, r);
        }
    }

    /**
     * 使用对数器测试.
     */
    public static void main(String[] args) {
        //CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV1()::sort, 1, 10, 1);
        //CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV1()::sort, 1, 10, 100);
        CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV1()::sort);
    }
}
