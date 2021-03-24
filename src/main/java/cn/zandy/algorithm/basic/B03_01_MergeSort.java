package cn.zandy.algorithm.basic;

import cn.zandy.algorithm.util.Comparator;

/**
 * 归并排序.
 *
 * 给定数组 由小到大 排序：
 *
 * 递归实现（左侧有序，右侧有序，merge两侧整体有序）
 *
 * 不熟练时，先画递归脑图来观察，再实现代码
 */
public class B03_01_MergeSort {

    private void sort(int[] arr) {
        range(arr, 0, arr.length - 1);
    }

    /**
     * 递归函数定义：让数组 arr 在下标 l->r 范围上有序（由小到大）
     */
    private void range(int[] arr, int l, int r) {
        if (arr == null) {
            throw new NullPointerException("待处理数组为null");
        }

        if (arr.length < 2) {
            return;
        }

        if (l > r || l < 0 || r > arr.length - 1) {
            throw new IllegalArgumentException("位置参数无效");
        }

        // "范围"只对应数组中一个元素，那一定有序，不需要再拆分，直接返回
        if (l == r) {
            return;
        }

        int mid = l + ((r - l) >> 2);
        range(arr, l, mid);
        range(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

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

    public static void main(String[] args) {
        Comparator.compareForSort(new B03_01_MergeSort()::sort);
    }
}
