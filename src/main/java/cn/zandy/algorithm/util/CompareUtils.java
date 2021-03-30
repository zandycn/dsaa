package cn.zandy.algorithm.util;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 对比工具.
 */
public class CompareUtils {

    public static final int EMPTY_FLAG = -100;
    public static final int NOT_FOUND_FLAG = -1;

    private static final int DEFAULT_NOT = 500000;
    private static final int DEFAULT_LEN = 100;
    private static final int DEFAULT_MAX = 100;

    public static void compareSortResultWithJDK(Consumer<int[]> c) {
        compareSortResultWithJDK(c, DEFAULT_NOT, DEFAULT_LEN, DEFAULT_MAX);
    }

    /**
     * 比较排序的对数器.
     *
     * @param not number of tests
     * @param len 每次生成的数组长度
     * @param max 数组中元素的最大值
     */
    public static void compareSortResultWithJDK(Consumer<int[]> c, int not, int len, int max) {
        long start = System.currentTimeMillis();

        not = not <= 0 ? DEFAULT_NOT : not;
        len = len <= 0 ? DEFAULT_LEN : len;
        max = max <= 0 ? DEFAULT_MAX : max;

        int[] array4User = null;
        int[] array4JDK = null;
        boolean result = true;

        for (int i = 1; i <= not; i++) {
            array4User = ArrayUtils.generateRandomArray(len, max);
            array4JDK = ArrayUtils.copy(array4User);
            //System.out.println("original arr : " + ArrayUtils.toString(array4User));

            c.accept(array4User);
            Arrays.sort(array4JDK); // 与 JDK 中的排序实现做对比

            result = ArrayUtils.equals(array4User, array4JDK);

            if (!result) {
                System.out.println("第" + i + "次测试时，出现差异！");
                System.out.println("array4User : " + ArrayUtils.toString(array4User));
                System.out.println("array4JDK  : " + ArrayUtils.toString(array4JDK));
                System.out.println("------------");
                break;
            }
        }

        System.out.println("对比结果：" + (result ? "无差异" : "存在差异！"));
        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");

        if (result) {
            System.out.println("------------");
            System.out.println("最后一次排序结果展示：");
            System.out.println("array4User : " + ArrayUtils.toString(array4User));
            System.out.println("array4JDK  : " + ArrayUtils.toString(array4JDK));
        }
    }

    public static <R> void compare4Bisection(BiFunction<int[], Integer, R> func1, BiFunction<int[], Integer, R> func2,
        Supplier<Integer> keyGen, boolean debugable) {
        compare4Bisection(func1, func2, keyGen, -1, -1, -1, debugable);
    }

    /**
     * 为二分法提供的对数器.
     *
     * @param func1 二分的实现
     * @param func2 粗暴的实现
     * @param keyGen searchKey生成器
     * @param totalTimes ...
     * @param initArrayLength ...
     * @param maxArrayLength ...
     * @param debug ...
     */
    public static <R> void compare4Bisection(BiFunction<int[], Integer, R> func1, BiFunction<int[], Integer, R> func2,
        Supplier<Integer> keyGen, int totalTimes, int initArrayLength, int maxArrayLength, boolean debug) {

        totalTimes = totalTimes <= 0 ? 10000 : totalTimes;
        initArrayLength = initArrayLength <= 0 ? 10 : initArrayLength;
        maxArrayLength = maxArrayLength <= 0 ? 100 : maxArrayLength;

        int searchKey;
        int[] arr;
        R res1, res2;
        boolean same;
        int totalCount = 0, sameCount = 0, differentCount = 0, exceptionCount = 0;

        long start = System.currentTimeMillis();

        for (int t = 1; t <= totalTimes; t++) {

            for (int i = initArrayLength; i <= maxArrayLength; i++) {
                searchKey = keyGen.get();

                totalCount++;

                arr = ArrayUtils.generateRandomArray(i, 100);
                Arrays.sort(arr);

                try {
                    res1 = func1.apply(arr, searchKey);
                } catch (Exception ex) {
                    System.out.println("exception sample: " + ArrayUtils.toString(arr));
                    exceptionCount++;
                    ex.printStackTrace();
                    continue;
                }

                res2 = func2.apply(arr, searchKey);

                same = res1.equals(res2);

                if (debug && res1 instanceof Integer) {
                    System.out.println("res1: " + res1 + ", res1: " + res1 + ", isSame: " + same);

                    if (((Integer) res1) == 0 || ((Integer) res2) == 0) {
                        System.out.println("searchKey:"
                            + searchKey
                            + ", res1: "
                            + res1
                            + ", res2: "
                            + res2
                            + ", zero sample: "
                            + ArrayUtils.toString(arr));
                    }

                    if (((Integer) res1) == NOT_FOUND_FLAG || ((Integer) res2) == NOT_FOUND_FLAG) {
                        System.out.println("searchKey:"
                            + searchKey
                            + ", res1: "
                            + res1
                            + ", res2: "
                            + res2
                            + ", not found sample: "
                            + ArrayUtils.toString(arr));
                    }
                }

                if (!same) {
                    System.out.println("searchKey:"
                        + searchKey
                        + ", res1: "
                        + res1
                        + ", res2: "
                        + res2
                        + ", different sample: "
                        + ArrayUtils.toString(arr));
                    differentCount++;
                } else {
                    sameCount++;
                }
            }
        }

        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");
        System.out.println("totalCount="
            + totalCount
            + ", sameCount="
            + sameCount
            + ", differentCount="
            + differentCount
            + ", exceptionCount="
            + exceptionCount);
    }

    public static void compareIntResult(int not, int len, int max, Function<int[], Integer> func1,
        Function<int[], Integer> func2) {
        not = not <= 0 ? DEFAULT_NOT : not;
        len = len <= 0 ? DEFAULT_LEN : len;
        max = max <= 0 ? DEFAULT_MAX : max;

        int[] arr;
        boolean result = true;
        int r1, r2;

        long start = System.currentTimeMillis();
        for (int i = 1; i <= not; i++) {
            arr = ArrayUtils.generateRandomArray(len, max);
            //System.out.println("arr : " + ArrayUtils.toString(arr));

            r1 = func1.apply(arr);
            r2 = func2.apply(arr);

            result = r1 == r2;

            if (!result) {
                System.out.println("第" + i + "次测试时，出现差异！");
                System.out.println("array : " + ArrayUtils.toString(arr));
                System.out.println("------------");
                break;
            }
        }

        System.out.println("对比结果：" + (result ? "无差异" : "存在差异！"));
        System.out.println("耗时：" + ((System.currentTimeMillis() - start) / 1000) + "秒");
    }
}
