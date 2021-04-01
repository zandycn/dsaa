package cn.zandy.algorithm.basic.c04;

/**
 * 堆的操作.
 */
public interface Heap<T> {

    /**
     * 插入一个元素.
     */
    void insert(T val);

    /**
     * Retrieves and removes the head of this heap,
     * or returns {@code null} if this heap is empty.
     *
     * @return the head of this heap, or {@code null} if this heap is empty
     */
    T poll();

    void heapify(int valIndex);

    /**
     * 堆是否满了.
     *
     * @return true-堆满了 false-堆没满
     */
    boolean isFull();

    /**
     * 是否是空堆.
     *
     * @return true-是 fasle-否
     */
    boolean isEmpty();
}
