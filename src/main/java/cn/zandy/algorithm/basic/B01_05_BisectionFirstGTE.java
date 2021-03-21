package cn.zandy.algorithm.basic;

import cn.zandy.algorithm.util.ArrayUtils;

import java.util.Arrays;

/**
 * 在一个【有序】数组中，找 >= 某个数最左侧的位置.
 *
 * 约定：
 * · 如果数组为空，返回 -100
 * · 如果传入数字比数组中所有元素都大，返回 -1
 * · 如果传入数字比数组中所有元素都小，返回 0
 */
public class B01_05_BisectionFirstGTE {

    private static final boolean DEBUG = false;

    private static final int EMPTY_FLAG = -100;

    private static final int NOT_FOUND_FLAG = -1;

    // O(logN)
    private int bisect(int[] arr, int n) {
        if (DEBUG) {
            System.out.println("\n\n------------ Method start ! n=" + n + "------------");
        }

        if (arr == null || arr.length == 0) {
            return EMPTY_FLAG;
        }

        int l = 0;
        int r = arr.length - 1;

        int loopCount = 0;
        int targetIndex = NOT_FOUND_FLAG;
        int midIndex;

        while (l <= r) {
            loopCount++;

            midIndex = l + ((r - l) >> 1);

            if (DEBUG) {
                System.out.println("Step " + loopCount + "\n");
                System.out.println(ArrayUtils.toFormattedString(arr, l, r));
                System.out.println("");
                System.out.println("(" + l + ", " + r + ") mid_index=" + midIndex + ", lastTargetIndex=" + targetIndex);
                System.out.println("------------");
            }

            if (arr[midIndex] >= n) {
                targetIndex = midIndex;
                r = midIndex - 1;
            } else {
                l = midIndex + 1;
            }
        }

        if (DEBUG) {
            System.out.println("last: (l=" + l + ", r=" + r + ")");
            System.out.println("------------ Method end ! targetIndex=" + targetIndex + "------------");
        }

        return targetIndex;
    }

    // O(N)
    private int iterate(int[] arr, int n) {
        if (arr == null || arr.length == 0) {
            return EMPTY_FLAG;
        }

        int targetIndex = NOT_FOUND_FLAG;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= n) {
                targetIndex = i;
                break;
            }
        }

        return targetIndex;
    }

    public static void main(String[] args) {
        B01_05_BisectionFirstGTE o = new B01_05_BisectionFirstGTE();
        int searchKey;

        long start = System.currentTimeMillis();

        int totalTimes = 10000;
        int initArrayLength = 10;
        int maxArrayLength = 100;

        int[] arr;
        int bResult, iResult;
        boolean same;
        int tc = 0, ec = 0, sc = 0, dc = 0;

        for (int t = 1; t <= totalTimes; t++) {

            for (int i = initArrayLength; i <= maxArrayLength; i++) {
                searchKey = (int) (Math.random() * 121 - Math.random() * 101);

                tc++;

                arr = ArrayUtils.generateRandomArray(i, 100);
                Arrays.sort(arr);

                try {
                    bResult = o.bisect(arr, searchKey);
                } catch (Exception ex) {
                    System.out.println("exception sample: " + ArrayUtils.toString(arr));
                    ec++;
                    ex.printStackTrace();
                    continue;
                }

                iResult = o.iterate(arr, searchKey);
                same = (bResult == iResult);

                if (DEBUG) {
                    System.out.println("bResult: " + bResult + ", iResult: " + iResult + ", isSame: " + same);

                    if (bResult == 0 || iResult == 0) {
                        System.out.println("searchKey:"
                            + searchKey
                            + ", r1: "
                            + bResult
                            + ", r2: "
                            + iResult
                            + ", zero sample: "
                            + ArrayUtils.toString(arr));
                    }

                    if (bResult == NOT_FOUND_FLAG || iResult == NOT_FOUND_FLAG) {
                        System.out.println("searchKey:"
                            + searchKey
                            + ", r1: "
                            + bResult
                            + ", r2: "
                            + iResult
                            + ", not found sample: "
                            + ArrayUtils.toString(arr));
                    }
                }

                if (!same) {
                    System.out.println("searchKey:"
                        + searchKey
                        + ", r1: "
                        + bResult
                        + ", r2: "
                        + iResult
                        + ", different sample: "
                        + ArrayUtils.toString(arr));
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
