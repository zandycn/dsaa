package cn.zandy.algorithm.basic.c01;

import cn.zandy.algorithm.util.ArrayUtils;

/**
 * 局部最小值————问题定义：
 *
 * 给定一个无序数组int[] a，这个无序数据组中任意两个相邻的数都不相等，这时我们说以下三种情况都可以称为局部最小：
 * · 1、当 a[0] < a[1] 时，a[0] 是局部最小
 * · 2、当 a[n] < a[n-1] 时，a[n] 是局部最小
 * · 3、数组中除0和n任意位置i的元素，如果满足 a[i] < a[i-1] 且 a[i] < a[i+1] 时，a[i] 是局部最小
 *
 * 有了以上的规则之后，我们要求找出数组中的任意一个局部最小值所在位置
 */
public class Code_07_BisectionAwesome {

    private static boolean debug;

    private static int bisect(int[] arr) {
        if (debug) {
            System.out.println("\n\n------------ Method start ! ------------");
        }

        if (arr == null || arr.length < 2) {
            throw new RuntimeException("数组最小长度为2");
        }

        if (arr[0] < arr[1]) {
            return 0;
        }

        int len = arr.length;

        if (arr[len - 1] < arr[len - 2]) {
            return len - 1;
        }

        int loopCount = 0;
        int l = 0, r = len - 1;
        //int l = 1, r = len - 2;
        int mid;
        while (l <= r) {
            loopCount++;

            mid = l + ((r - l) >> 1);

            if (debug) {
                System.out.println("Step " + loopCount + "\n");
                System.out.println(ArrayUtils.toFormattedString(arr, l, r));
                System.out.println("");
                System.out.println("(" + l + ", " + r + ") mid_index=" + mid);
                System.out.println("------------");
            }

            // ①如果 mid 位置是局部最小值，就直接返回
            if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
                if (debug) {
                    System.out.println("Got!! loopCount:" + loopCount);
                    System.out.println("------------ Method end ! ------------");
                }
                return mid;
            }

            if (arr[mid] < arr[mid + 1]) {
                // 如果进入这个分支，再结合 ① 可以推出 arr[mid] > arr[mid - 1]，
                // 那么 (mid-1)->mid 呈向上趋于，mid->(mid+1) 也呈向上趋于，所以以下两次赋值（覆盖图形）方式我觉得都可以
                r = mid + 1;
                //r = mid;
            } else {
                // 如果进入这个分支，arr[mid] > arr[mid + 1]， 那么 mid->(mid+1) 呈向下趋于， (mid-1)->mid 趋势不确定
                l = mid;
            }
        }

        if (debug) {
            System.out.println("Not Found!! loopCount:" + loopCount);
            System.out.println("------------ Method end ! ------------");
        }

        return -1;
    }

    private static void test() {
        int totalTimes = 100000, initArrayLength = 2, maxArrayLength = 200;

        int c = 0, rc = 0, nfc = 0;

        int[] arr;
        int r;

        long start = System.currentTimeMillis();
        for (int t = 1; t <= totalTimes; t++) {

            for (int i = initArrayLength; i <= maxArrayLength; i++) {
                c++;

                arr = ArrayUtils.generateAdjacentUnequalArray(i, 20);

                r = bisect(arr);

                if (debug) {
                    System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
                    System.out.println("局部最小值: " + r);
                    System.out.println("-------------------");
                }

                if (r == -1) {
                    nfc++;
                    continue;
                }

                if ((r == 0 && arr[0] < arr[1]) || (r == arr.length - 1 && arr[arr.length - 1] < arr[arr.length - 2])) {
                    rc++;
                    continue;
                }

                if (arr[r] < arr[r - 1] && arr[r] < arr[r + 1]) {
                    rc++;
                }
            }
        }

        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");
        System.out.println("loopCount=" + c + ", rightCount=" + rc + ", notFoundCount=" + nfc);
    }

    public static void main(String[] args) {
        test();

        /*int[] case1 = {30, 21, 14, 29, 14, 31, 16, 1, 14, 23, 34, 17, 26, 7, 4, 3, 30, 7, 22, 23, 14, 33, 28, 5, 28, 21,
            8, 33, 26, 7, 4, 15, 22, 37, 28, 1, 12, 21};
        int[] case2 = {32, 1, 22, 2, 25};

        debug = true;
        int r1 = bisect(case1);
        int r2 = bisect(case2);
        System.out.println("case1 局部最小值: " + r1);
        System.out.println("case2 局部最小值: " + r2);*/
    }
}
