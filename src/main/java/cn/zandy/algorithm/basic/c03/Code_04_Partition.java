package cn.zandy.algorithm.basic.c03;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.ParamCheckUtils;

import java.util.ArrayList;
import java.util.List;

public class Code_04_Partition {

    private boolean debug;

    /**
     * 【Partition 问题】
     * 给定一个数组，和一个整数num。请把小于等于num的数放在数组的左边，大于num的数放在数组的右边。
     * 要求额外空间复杂度O(1)，时间复杂度O(N)
     */
    private void partition(int[] arr, int num) {
        if (arr == null) {
            return;
        }

        partition(arr, num, 0, arr.length - 1);
    }

    /**
     * @return 返回数组中第一个大于num的元素位置，若没有则返回-1
     */
    public int partition(int[] arr, int num, int l, int r) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        ParamCheckUtils.checkLR(arr.length, l, r);

        int lti = l - 1; // 小于区开始指针位置
        int i = l;       // 遍历指针：代表下一次要检查的位置

        // ① arr[i] <= num, [i] 与 小于区右测元素交换，小于区右扩，遍历指针右移(i++)
        // ② arr[i] > num, 此时只需右移遍历指针(i++)
        while (i <= r) {
            if (arr[i] <= num) {
                ArrayUtils.swap(arr, i++, ++lti);
            } else {
                i++;
            }
        }

        return (lti + 1) > r ? -1 : lti + 1;
    }

    /**
     * 【荷兰国旗问题】
     * 给定一个数组，和一个整数num。请把小于num的数放在数组的左边，等于num的数放在中间，大于num的数放在数组的右边。
     * 要求额外空间复杂度O(1)，时间复杂度O(N)
     *
     * @return 返回只有两个元素的int数组
     */
    private int[] flagOfNED(int[] arr, int num) {
        int lti = -1;         // 小于区开始指针位置
        int gti = arr.length; // 大于区开始指针位置
        int i = 0;            // 遍历指针：代表下一次要检查的位置

        // ① arr[i] < num, [i] 与 小于区右测元素交换，小于区右扩，遍历指针右移(i++)
        // ② arr[i] == num, 此时只需右移遍历指针(i++)
        // ③ arr[i] > num, [i] 与 大于区左测元素交换，大于区左扩，遍历指针不动
        while (i < gti) {
            if (arr[i] < num) {
                ArrayUtils.swap(arr, i++, ++lti);
            } else if (arr[i] == num) {
                i++;
            } else {
                ArrayUtils.swap(arr, i, --gti);
            }
        }

        // 返回值:
        // 元素一: 数组中最后一个小于num的元素位置，若没有则返回-1
        // 元素二: 数组中第一个大于num的元素位置，若没有则返回-1
        return new int[] {lti, gti == arr.length ? -1 : gti};
    }

    private int[] flagOfNED1(int[] arr, int num) {
        List<Integer> ltList = new ArrayList<>();
        List<Integer> sameList = new ArrayList<>();
        //List<Integer> gtList = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < num) {
                ltList.add(arr[i]);
            } else if (arr[i] == num) {
                sameList.add(arr[i]);
            } else {
                //gtList.add(arr[i]);
            }
        }

        int gti = ltList.size() + sameList.size();
        return new int[] {ltList.size() - 1, gti == arr.length ? -1 : gti};
    }

    public static void main(String[] args) {
        testFlagOfNED();
    }

    /**
     * 测试荷兰国旗问题.
     */
    private static void testFlagOfNED() {
        Code_04_Partition ins = new Code_04_Partition();
        //ins.debug = true;

        int totalTimes = 5000;
        int minLen = 0;
        int maxLen = 200;

        int loopCount = 0, diffCount = 0;

        long start = System.currentTimeMillis();
        for (int t = 0; t < totalTimes; t++) {

            for (int i = minLen; i <= maxLen; i++) {
                int[] arr = ArrayUtils.generateRandomArray(i, 100);
                int[] arr1 = ArrayUtils.copy1(arr);

                int num = (int) (Math.random() * 121 - Math.random() * 101);
                int[] r1 = ins.flagOfNED(arr, num);
                int[] r2 = ins.flagOfNED1(arr1, num);

                boolean r = ArrayUtils.equals(r1, r2);

                if (ins.debug) {
                    System.out.println("number = " + num);
                    System.out.println(ArrayUtils.toFormattedString(arr, 0, arr.length - 1));
                    System.out.println("r1 : " + ArrayUtils.toString(r1));
                    System.out.println("r2 : " + ArrayUtils.toString(r2));
                    System.out.println("r1 = r2 ? " + r);
                }

                if (!r) {
                    diffCount++;
                }
                loopCount++;
            }
        }

        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");
        System.out.println("loopCount=" + loopCount + ", diffCount=" + diffCount);
    }
}
