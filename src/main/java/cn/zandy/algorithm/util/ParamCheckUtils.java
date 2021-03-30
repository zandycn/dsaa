package cn.zandy.algorithm.util;

/**
 * Created by zandy on 2021/3/30.
 */
public class ParamCheckUtils {

    public static void checkLR(int len, int l, int r) {
        if (l > r || l < 0 || r > len - 1) {
            throw new IllegalArgumentException("传入的位置参数[" + l + "," + r + "]错误");
        }
    }
}
