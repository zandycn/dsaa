package cn.zandy.algorithm.util;

/**
 * 数组工具类.
 */
public class ArrayUtils {

    public static int[] generateRandomArray(int len, int max) {
        int[] arr = new int[len];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (max + 1)); // [0,max+1) -> [0,max]
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
        int[] arr = generateRandomArray(10, 10);
        System.out.println(toFormattedString(arr, 0, arr.length - 1));
    }
}
