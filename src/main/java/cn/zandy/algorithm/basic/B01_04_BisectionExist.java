package cn.zandy.algorithm.basic;

import cn.zandy.algorithm.util.ArrayUtils;

import java.util.Arrays;

/**
 * 在一个【有序】数组中，找某个数是否存在.
 */
public class B01_04_BisectionExist {

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

            // R-L == 0 时，mi=L=R; R-L == 1 时，mi=L<R; R-L >= 2 时，L<mi<R
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
        B01_04_BisectionExist o = new B01_04_BisectionExist();
        int searchKey = 50;

        //int[] tArr = {13, 21, 29, 50, 56, 68, 81, 87};
        //System.out.println(e.exist(tArr, searchKey));
        //System.out.println(Arrays.binarySearch(tArr, searchKey));

        long start = System.currentTimeMillis();

        int totalTimes = 20000;
        int initArrayLength = 10;
        int maxArrayLength = 500;

        int[] arr;
        boolean myResult;
        int jdkResult;
        boolean same;
        int tc = 0, ec = 0, sc = 0, dc = 0;

        for (int t = 1; t <= totalTimes; t++) {

            for (int i = initArrayLength; i <= maxArrayLength; i++) {
                tc++;

                arr = ArrayUtils.generateRandomArray(i, 100);
                Arrays.sort(arr);

                try {
                    myResult = o.exist(arr, searchKey);
                } catch (Exception ex) {
                    System.out.println("exception sample: " + ArrayUtils.toString(arr));
                    ec++;
                    ex.printStackTrace();
                    continue;
                }

                jdkResult = Arrays.binarySearch(arr, searchKey);
                same = (myResult == (jdkResult >= 0));

                if (DEBUG) {
                    System.out.println("myResult: " + myResult + ", jdkResult: " + jdkResult + ", isSame: " + same);
                }

                if (!same) {
                    System.out.println("different sample: " + ArrayUtils.toString(arr));
                    dc++;
                } else {
                    sc++;
                }
            }
        }

        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");
        System.out.println("tc=" + tc + ", sc=" + sc + ", dc=" + dc + ", ec=" + ec);
    }
}
