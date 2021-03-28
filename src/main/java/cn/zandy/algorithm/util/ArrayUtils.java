package cn.zandy.algorithm.util;

/**
 * 数组工具类.
 */
public class ArrayUtils {

    /**
     * 生成长度为 len，元素大小在区间 [0,max] 的数组.
     */
    public static int[] generateRandomArray(int len, int max) {
        int[] arr = new int[len];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (max + 1)); // [0,max+1) -> [0,max]
        }

        return arr;
    }

    /**
     * 生成相邻两个数不等的数组.
     */
    public static int[] generateAdjacentUnequalArray(int len, int base) {
        int[] arr = new int[len];

        int tmp;
        for (int i = 0; i < arr.length; i++) {
            tmp = (int) (Math.random() * (base + 1));
            if (i % 2 == 0) {
                arr[i] = tmp << 1;
            } else {
                arr[i] = tmp << 1 | 1;
            }
        }

        return arr;
    }

    /**
     * 生成这样的数组：
     * 数组上最后一个数是num，对于num之前的元素，小于num的在左侧，大于num的在右侧，整个数组可能无序。
     *
     * @param len 数组的长度
     * @param num 指定数组上最后一个数
     */
    public static int[] generatePartitionArray(int len, int num) {
        if (len <= 0) {
            throw new IllegalArgumentException("len必须大于0");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("num必须大于0");
        }

        int[] arr = new int[len];
        arr[len - 1] = num;

        if (len > 1) {
            int otherNumCount = len - 1;

            //  Math.random()                   : [0.0,   1.0)
            //  Math.random() * num             : [0.0,   num)
            //  Math.random() + 1               : [1.0,   2.0)
            // (Math.random() + 1) * (num + 1)  : [num+1, 2num+2)

            int lessthanNumCount = (int) (Math.random() * otherNumCount);

            // 生成比num小的数字，并放到左边
            for (int i = 0; i < lessthanNumCount; i++) {
                arr[i] = (int) (Math.random() * num); // 小于 num 的数
            }

            // 生成比num大的数字，依次放到右边
            for (int i = lessthanNumCount; i < otherNumCount; i++) {
                arr[i] = (int) ((Math.random() + 1) * (num + 1)); // 大于 num 的数
            }
        }

        return arr;
    }

    public static int[] copy(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new NullPointerException("无效的数组！");
        }

        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }

        return copy;
    }

    public static int[] copy1(int[] arr) {
        if (arr == null) {
            throw new NullPointerException("无效的数组！");
        }

        int[] copy = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }

        return copy;
    }

    /**
     * java.util.Arrays.equals(int[] a, int[] a2)
     */
    public static boolean equals(int[] a, int[] a2) {
        if (a == a2) {
            return true;
        }

        if (a == null || a2 == null) {
            return false;
        }

        int length = a.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (a[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * java.util.Arrays.toString(int[] a)
     */
    public static String toString(int[] a) {
        if (a == null) {
            return "null";
        }

        int iMax = a.length - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(", ");
        }
    }

    public static String toFormattedString(int[] a, int leftIndex, int rightIndex) {
        if (a == null) {
            return "null";
        }

        if (leftIndex > rightIndex) {
            return "[]";
        }

        StringBuilder iBuilder = new StringBuilder();
        StringBuilder oBuilder = new StringBuilder();
        iBuilder.append("Index|");
        oBuilder.append("Array[");

        for (int i = leftIndex; ; i++) {
            iBuilder.append(String.format("%5s", i));
            oBuilder.append(String.format("%5s", a[i]));

            if (i == rightIndex) {
                return iBuilder.append("|\n").append(oBuilder).append(']').toString();
            }
            iBuilder.append("| ");
            oBuilder.append(", ");
        }
    }

    public static void swap(int[] arr, int idx1, int idx2) {
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }

    public static void swap1(int[] arr, int idx1, int idx2) {
        arr[idx1] = arr[idx1] ^ arr[idx2];
        arr[idx2] = arr[idx1] ^ arr[idx2];
        arr[idx1] = arr[idx1] ^ arr[idx2];
    }

    public static void main(String[] args) {
        System.out.println("------------随机数组--------------");
        int[] arr = generateRandomArray(10, 10);
        System.out.println(toFormattedString(arr, 0, arr.length - 1));
        System.out.println("");

        System.out.println("------------连续两数不等(偶奇)数组--------------");
        int[] arr1 = generateAdjacentUnequalArray(10, 10);
        System.out.println(toFormattedString(arr1, 0, arr.length - 1));
        System.out.println("");

        System.out.println("------------以最后一个元素partition的数组--------------");
        int[] arr2 = generatePartitionArray(10, 10);
        System.out.println(toFormattedString(arr2, 0, arr.length - 1));
        System.out.println("");
    }
}
