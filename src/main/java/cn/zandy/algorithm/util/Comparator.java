package cn.zandy.algorithm.util;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 对比工具.
 */
public class Comparator {

    /**
     * 排序对数器.
     */
    public static void compareForSort(Consumer<int[]> c) {
        long start = System.currentTimeMillis();

        int not = 500000; // number of tests
        int len = 100;    // 每次生成的数组长度
        int max = 100;    // 数组中元素的最大值

        int[] mySortArray = null;
        int[] jdkSortArray = null;
        boolean result = true;

        for (int i = 1; i <= not; i++) {
            mySortArray = ArrayUtils.generateRandomArray(len, max);
            jdkSortArray = ArrayUtils.copy(mySortArray);
            //System.out.println("original arr : " + ArrayUtils.toString(mySortArray));

            c.accept(mySortArray);
            Arrays.sort(jdkSortArray); // 与 jdk 中的排序实现做对比

            result = ArrayUtils.equals(mySortArray, jdkSortArray);

            if (!result) {
                System.out.println("第" + i + "次测试时，出现差异！");
                System.out.println("mySortArray  : " + ArrayUtils.toString(mySortArray));
                System.out.println("jdkSortArray : " + ArrayUtils.toString(jdkSortArray));
                System.out.println("------------");
                break;
            }
        }

        System.out.println("对比结果：" + result);
        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");

        if (result) {
            System.out.println("------------");
            System.out.println("最后一次排序结果展示：");
            System.out.println("mySortArray  : " + ArrayUtils.toString(mySortArray));
            System.out.println("jdkSortArray : " + ArrayUtils.toString(jdkSortArray));
        }
    }
}
