package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

/**
 * 【快排】第二个版本，基于 荷兰国旗
 *
 * 以数组 最后一个元素 进行 荷兰国旗, 然后将其和小于区下一个元素交换，
 * 再将左右两侧递归 以同样方式进行 荷兰国旗.
 *
 * 与 第一版 的区别：partition 一次确定一个数的位置；荷兰国旗一次可能确定一组(相等)数的位置
 *
 * 时间复杂度: O(N^2), 受随机生成的样本数据状况影响，当样本数据刚生成就有序时 复杂度最高。
 */
public class Code_05_QuickSortV2 {

    private static final boolean DEBUG = false;

    private static long c = 0;

    private Code_04_Partition ins = new Code_04_Partition();

    private void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        recursion(arr, 0, arr.length - 1, false);
    }

    /**
     * 递归函数定义：保证数组 l->r 范围内有序.
     *
     * @param isRandomQuickSort true-随机快排(for v3)  false-(for v2)
     */
    void recursion(int[] arr, int l, int r, final boolean isRandomQuickSort) {
        if (l == r) {
            return;
        }

        if (DEBUG) {
            System.out.println("\n----------------");
            System.out.println("第" + (++c) + "次递归【开始】，范围 [" + l + ", " + r + "]");
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
            System.out.println("");
        }

        if (isRandomQuickSort) {
            // 随机快排，将 l->r 任意一个位置值与 arr[r] 交换
            ArrayUtils.swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        }

        int[] indexArr = ins.flagOfNED(arr, arr[r], l, r - 1);
        int lastLessIndex = indexArr[0];    // 取值 -1 或 [l,r-1] 区间
        int firstLargerIndex = indexArr[1]; // 取值 -1 或 [l,r-1] 区间

        if (DEBUG) {
            System.out.println("lastLessIndex=" + lastLessIndex + ",firstLargerIndex=" + firstLargerIndex);
            System.out.println("partition之后的数组：");
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
        }

        if (lastLessIndex == firstLargerIndex) {
            // lastLessIndex, firstLargerIndex 都是 -1
            if (DEBUG) {
                System.out.println("数组中所有元素相等，都是 " + arr[r]);
            }
        } else {
            if (firstLargerIndex == -1) {
                // 这里 lastLessIndex 一定不是 -1
                recursion(arr, l, lastLessIndex, isRandomQuickSort);
            } else {
                ArrayUtils.swap(arr, firstLargerIndex, r);
                recursion(arr, firstLargerIndex + 1, r, isRandomQuickSort);

                if (lastLessIndex != -1) {
                    recursion(arr, l, lastLessIndex, isRandomQuickSort);
                }
            }
        }
    }

    /**
     * 使用对数器测试.
     */
    public static void main(String[] args) {
        //CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV2()::sort, 1, 10, 1);
        //CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV2()::sort, 1, 10, 100);
        CompareUtils.compareSortResultWithJDK(new Code_05_QuickSortV2()::sort);
    }
}
