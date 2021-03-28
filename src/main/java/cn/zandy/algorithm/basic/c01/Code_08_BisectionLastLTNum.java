package cn.zandy.algorithm.basic.c01;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.CompareUtils;

/**
 * 【问题定义】
 * 如果数组上最后一个数是num，对于num之前的元素，小于num的在左侧，大于num的在右侧，整个数组可能无序。
 * 要去找出小于num的最后一个数的位置。
 */
public class Code_08_BisectionLastLTNum {

    private static boolean debug;

    private static int bisect(int[] arr) {
        if (debug) {
            System.out.println("\n\n------------ Method start ! ------------");
            System.out.println("原始数组如下：");
            System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
            System.out.println("------------\n");
        }

        if (arr == null || arr.length < 2) {
            return CompareUtils.NOT_FOUND_FLAG;
        }

        int num = arr[arr.length - 1];

        int l = 0, r = arr.length - 2;
        int mid;

        int loopCount = 0;
        int resIndex = CompareUtils.NOT_FOUND_FLAG;

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

            if (arr[mid] < num) {
                resIndex = mid;
                l = mid + 1;
            } else if (arr[mid] == num) {
                // ignore 根据问题定义，不可能有与num相同的
            } else {
                r = mid - 1;
            }
        }

        if (debug) {
            System.out.println("------------ Method end ! ------------");
        }

        return resIndex;
    }

    private static void test() {
        int totalTimes = 500000, initArrayLength = 1, maxArrayLength = 200, num = 20;

        int c = 0, rc = 0, nfc = 0, ec = 0;

        int[] arr;
        int r;

        long start = System.currentTimeMillis();
        for (int t = 1; t <= totalTimes; t++) {

            for (int i = initArrayLength; i <= maxArrayLength; i++) {
                c++;

                arr = ArrayUtils.generatePartitionArray(i, num);

                r = bisect(arr);

                if (debug) {
                    System.out.println("找到的位置: " + r);
                    System.out.println("-------------------");
                }

                if (r == -1) {
                    if (arr[0] >= num) {
                        nfc++;
                    } else {
                        if (debug) {
                            System.out.println("结果错误！！");
                        }
                        ec++;
                    }
                    continue;
                }

                if (arr[r] < num && arr[r + 1] >= num) {
                    rc++;
                } else {
                    if (debug) {
                        System.out.println("结果错误！！");
                    }
                    ec++;
                }
            }
        }

        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");
        System.out.println("loopCount=" + c + ", rightCount=" + rc + ", notFoundCount=" + nfc + ", errorCount=" + ec);
    }

    public static void main(String[] args) {
        //debug = true;
        test();
    }
}
