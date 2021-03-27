package cn.zandy.algorithm.basic.c01;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 在一个【有序】数组中，找某个数是否存在.
 */
public class Code_04_BisectionExist {

    private static final boolean DEBUG = false;

    private boolean exist(int[] arr, int n) {
        if (DEBUG) {
            System.out.println("\n\n------------ Method start ! ------------");
        }

        if (arr == null || arr.length == 0) {
            return false;
        }

        int l = 0;
        int r = arr.length - 1;

        /*// n 比数组的最小值小 或 比数组的最大值大
        if (n < arr[l] || arr[r] < n) {
            return false;
        }去掉这里的判断，纯用二分实现*/

        int loopCount = 0, equalCount = 0;
        int midIndex;

        while (l <= r) {
            loopCount++;

            // R-L == 0 时，L=mi=R
            // R-L == 1 时，L=mid<R
            // R-L >= 2 时，L<mi<R
            // 一直向左二分(至R<L)，不一定会出现 R=L 的情况；一直向右二分(至L>R)，一定会出现 R=L 的情况
            if (l == r) {
                equalCount++;
            }

            // 获取中间位置（这种方式可避免溢出）
            midIndex = l + ((r - l) >> 1);

            if (DEBUG) {
                System.out.println("Step " + loopCount + "\n");
                System.out.println(ArrayUtils.toFormattedString(arr, l, r));
                System.out.println("");
                System.out.println("(" + l + ", " + r + ") mid_index=" + midIndex);
                System.out.println("------------");
            }

            if (arr[midIndex] == n) {
                if (DEBUG) {
                    System.out.println("Exist!! loopCount:" + loopCount + ", equalCount:" + equalCount);
                    System.out.println("------------ Method end ! ------------");
                }
                return true;
            } else if (arr[midIndex] < n) {
                l = midIndex + 1;
            } else {
                r = midIndex - 1;
            }
        }

        if (DEBUG) {
            System.out.println("NoExist!! loopCount:" + loopCount + ", equalCount:" + equalCount);
            System.out.println("------------ Method end ! ------------");
        }
        return false;
    }

    public static void main(String[] args) {
        Code_04_BisectionExist ins = new Code_04_BisectionExist();

        Supplier<Integer> kenGen = () -> 50;

        BiFunction<int[], Integer, Boolean> f2 = (arr, key) -> Arrays.binarySearch(arr, key) >= 0;

        CompareUtils.compare4Bisection(ins::exist, f2, kenGen, 20000, 10, 500, DEBUG);
    }
}
