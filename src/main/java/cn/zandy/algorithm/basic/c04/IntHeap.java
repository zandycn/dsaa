package cn.zandy.algorithm.basic.c04;

import cn.zandy.algorithm.util.ArrayUtils;
import cn.zandy.algorithm.util.BTreePrinter;

/**
 * 堆结构:
 *
 * 堆是一种【特殊的完全二叉树】结构，分为 大根堆 和 小根堆.
 *
 * 大根堆：在完全二叉树中，每棵子树的【最大值都在顶部】（每个父节点都>=它左右孩子（如果有））
 * 小根堆：在完全二叉树中，每棵子树的【最小值都在顶部】（每个父节点都<=它左右孩子（如果有））
 *
 * <p>
 * heap insert 是组织堆结构的一种方式
 * · 它的功能是：用户给定一个元素，我们放到数组的下一个空位置并进行调整，使这个数组依然是堆结构
 * · 调整过程：
 * ·   大根堆——将元素放到数组下一个空位置后，把它和父节点比较，比父节点【大】就交换...
 * ·          一直持续这个比较和交换直到【它不大于父节点】或【它位置为0】时停止（参见 {@link #insert(Integer)}）
 * ·   小根堆——将元素放到数组下一个空位置后，把它和父节点比较，比父节点【小】就交换...
 * ·          一直持续这个比较和交换直到【它不小于父节点】或【它位置为0】时停止
 * · 调整代价：logN
 *
 * 指定堆中一个位置，进行 heapify 是这样一个过程：
 * ·  大根堆——将该位置的元素和它的子节点比较，如果有子节点比它【大】，要把它和最【大】的子节点做交换，然后拿它的新位置继续做 ify...
 * ·         这个过程一直持续到【它没有任何子节点】或【它的所有子节点都不大于它】时停止（参见 {@link #heapify(int)}）
 * ·  小根堆——将该位置的元素和它的子节点比较，如果有子节点比它【小】，要把它和最【小】的子节点做交换，然后拿它的新位置继续做 ify...
 * ·         这个过程一直持续到【它没有任何子节点】或【它的所有子节点都不小于它】时停止
 * · 调整代价：logN
 *
 * <p>
 * 用户给定一个数组(完全二叉树结构)，我们将其调整成堆，一般有两种方法
 * 1、遍历数组，将每一个元素进行 heap insert, 时间复杂度 O(N * logN)
 * 2、从后向前遍历数组，将每一个元素进行 heapify, 时间复杂度收敛于 O(N) （参见 {@link #IntHeap(Integer[], boolean)}）
 * 说明：
 * 第1种方法通过 heap insert, 因为是【从上向下】建堆，每层【元素数量由少变多】【调整代价由小变大】，所以复杂度较高
 * 第2种方法通过 heapify, 因为是【从下向上】进行调整，每层【元素数量由多变少】【调整代价由小变大】，所以复杂度相对低一些
 *
 * <p>
 * 说明：此类默认是【大根堆】的实现.
 *
 * {@link java.util.PriorityQueue} 默认是【小根堆】的实现.
 */
public final class IntHeap implements Heap<Integer> {

    private Integer[] arr;

    /**
     * 由用户指定从 0或1 开始建堆
     *
     * true-从数组第【0】个位置开始建堆  false-从数组第【1】个位置开始建堆
     */
    private final boolean is_at0;

    /**
     * 下一个要插入的位置.
     */
    private int next;

    /**
     * 创建一个空的堆.
     *
     * @param len 最多容纳节点数量
     * @param startAt0 see is_at0
     */
    public IntHeap(int len, boolean startAt0) {
        arr = new Integer[len];

        is_at0 = startAt0;

        next = getNextInitVal();
    }

    /**
     * 根据给定的数组，生成一个堆.
     *
     * @param array 数组
     * @param startAt0 see is_at0
     */
    public IntHeap(Integer[] array, boolean startAt0) {
        arr = array;

        is_at0 = startAt0;

        next = getLength();

        for (int i = getLength() - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * 组织成大根堆.
     */
    @Override
    public void insert(Integer val) {
        if (isFull()) {
            throw new RuntimeException("堆已满！");
        }

        arr[next] = val;

        int ci = getAndIncr();  // val 插入在数组中的位置
        int pi = getParent(ci); // val 父节点在数组中的位置

        while (ci != 0 && val > arr[pi]) {
            ArrayUtils.swap(arr, pi, ci);

            ci = pi;
            pi = getParent(pi);
        }
    }

    @Override
    public Integer poll() {
        if (isEmpty()) {
            return null;
        }

        Integer head = arr[0];

        // 缩小堆范围
        decr();

        // 把堆中最后一个节点换到头部（这时头节点被换到非堆范围的位置）
        ArrayUtils.swap(arr, next, 0);

        heapify(0);

        return head;
    }

    @Override
    public void heapify(int valIndex) {
        Integer lci; // 左孩子节点在数组中的位置
        Integer rci; // 右孩子节点在数组中的位置

        int mvi = valIndex; // max value index
        int premvi;
        for (; ; ) {
            premvi = mvi;

            lci = getLeftChild(mvi);
            rci = getRightChild(mvi);

            if (lci != null && arr[mvi] < arr[lci]) {
                mvi = lci;
            }
            if (rci != null && arr[mvi] < arr[rci]) {
                mvi = rci;
            }

            if (mvi == premvi) {
                break;
            }

            ArrayUtils.swap(arr, premvi, mvi);
        }
    }

    @Override
    public boolean isFull() {
        return next == arr.length;
    }

    @Override
    public boolean isEmpty() {
        return next == getNextInitVal();
    }

    private int getLength() {
        return arr.length;
    }

    private int getNextInitVal() {
        return is_at0 ? 0 : 1;
    }

    private int getAndIncr() {
        int cur = next;

        if (!isFull()) {
            next++;
        }

        return cur;
    }

    private void decr() {
        if (!isEmpty()) {
            next--;
        }
    }

    /* ---TAG--- 适用于从数组第0、1个位置开始建堆 ------------ */

    /**
     * @param index 元素位置
     * @return 左孩子在数组中的位置
     */
    private Integer getLeftChild(int index) {
        int lci;
        if (is_at0) {
            lci = 2 * index + 1;
        } else {
            lci = index << 1;
        }

        if (lci >= next) {
            return null;
        }

        return lci;
    }

    private Integer getRightChild(int index) {
        int rci;
        if (is_at0) {
            rci = 2 * index + 2;
        } else {
            rci = index << 1 | 1;
        }

        if (rci >= next) {
            return null;
        }

        return rci;
    }

    private int getParent(int i) {
        return is_at0 ? (i - 1) / 2 : i >> 1;
    }

    /* ---END--- 适用于从数组第0、1个位置开始建堆 ------------ */

    private boolean isMaxHeap() {
        if (next == getNextInitVal()) {
            return false;
        }

        Integer lci, rci;
        for (int i = 0; i < arr.length; i++) {
            lci = getLeftChild(i);
            rci = getRightChild(i);

            if (lci != null && arr[i] < arr[lci]) {
                return false;
            }

            if (rci != null && arr[i] < arr[rci]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        IntHeap imh = new IntHeap(32, true);

        System.out.println("heap insert 建堆过程：");
        for (int i = 0; i < 16; i++) {
            System.out.println("--------------------------");
            int ran = (int) (Math.random() * 100);
            imh.insert(ran);
            System.out.println("插入值【" + ran + "】");
            System.out.println("插入后是否是大根堆：" + imh.isMaxHeap());
            System.out.println("");

            BTreePrinter.Node<Integer> node = ArrayUtils.array2Tree(imh.arr, imh::getLeftChild, imh::getRightChild);
            BTreePrinter.printNode(node);
        }

        System.out.println("====================================================");

        System.out.println("poll 过程：");
        for (int i = 1; i <= 5; i++) {
            System.out.println("-------------第" + i + "次 poll-------------");
            System.out.println("poll前next=" + imh.next);
            System.out.println("poll结果=" + imh.poll());
            System.out.println("poll后next=" + imh.next);
            System.out.println("poll后是否是大根堆：" + imh.isMaxHeap());
            System.out.println("");

            BTreePrinter.Node<Integer> node = ArrayUtils.array2Tree(imh.arr, imh::getLeftChild, imh::getRightChild);
            BTreePrinter.printNode(node);
        }

        System.out.println("====================================================");
        System.out.println("将数组调整成大根堆：");
        System.out.println("调整前：");
        int[] tmpArray = ArrayUtils.generateRandomArray(10, 100);
        System.out.println(ArrayUtils.toString(tmpArray));
        Integer[] array = ArrayUtils.boxing(tmpArray);

        IntHeap imh1 = new IntHeap(array, true);
        System.out.println("调整后：");
        BTreePrinter.Node<Integer> node1 = ArrayUtils.array2Tree(imh1.arr, imh1::getLeftChild, imh1::getRightChild);
        BTreePrinter.printNode(node1);
        System.out.println("next=" + imh1.next);
    }
}
