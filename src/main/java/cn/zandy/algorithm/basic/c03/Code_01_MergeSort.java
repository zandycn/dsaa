package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.util.CompareUtils;

/**
 * 归并排序.
 *
 * 给定数组 由小到大 排序：
 *
 * 这里采用【递归】和【非递归】两种方式实现
 */
public class Code_01_MergeSort {

    private void recurSort(int[] arr) {
        recursion(arr, 0, arr.length - 1);
    }

    /**
     * 【递归实现】
     * 左侧排好序，右侧排好序，merge两侧让整体有序。注：不熟练时，先画递归脑图来观察，再实现代码
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

    // 2N, 去掉常数项为 O(N)
    private void merge(int[] arr, int l, int mid, int r) {
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
    }

    /**
     * 非递归实现
     *
     * TODO
     */
    private void iterativeSort(int[] arr) {
        int point = 1;

        while (point < arr.length - 1) {

        }
    }

    public static void main(String[] args) {
        CompareUtils.compareSortResultWithJDK(new Code_01_MergeSort()::recurSort);
    }
}
