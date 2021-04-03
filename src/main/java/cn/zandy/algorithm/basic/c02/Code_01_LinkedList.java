package cn.zandy.algorithm.basic.c02;

/**
 * 单向链表.
 */
public class Code_01_LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    public void addIfNotNull(T t) {
        if (t == null) {
            return;
        }

        if (head == null) {
            head = new Node<T>(t);
            tail = head;
        } else {
            Node<T> n = new Node<T>(t);
            tail.next = n;
            tail = n;
        }
    }

    /**
     * 反转链表.
     */
    public void revert() {
        if (head == null || head == tail) {
            return;
        }

        // 上一个被反转节点 的指针
        Node<T> pre = null;
        // 当前需要被反转节点 的指针
        Node<T> cur = head;
        // 下一个将要被反转节点 的指针
        Node<T> next;

        while (cur != null) {
            // 记录 下一个将要被反转的节点
            next = cur.next;

            // 【反转动作】将当前节点反转，指向前一个节点
            cur.next = pre;

            // 反转之后，从新定义 pre 和 cur
            pre = cur;
            cur = next;
        }

        tail = head;
        tail.next = null;
        head = pre;
    }

    /**
     * 删除链表中与给定值相等的节点.
     *
     * @return 删除成功的节点个数.
     */
    public long delete(T val) {
        if (val == null || head == null) {
            return 0;
        }

        int dc = 0;
        // 判断头节点是否需要删除，定义新头部
        while (val.equals(head.value)) {
            head = head.next;
            dc++;
        }

        // 永远指向上一个不相等的节点
        Node<T> pre = head;
        // 当前需要判断的节点
        Node<T> cur = head.next;

        while (cur != null) {
            if (!val.equals(cur.value)) {
                pre.next = cur;
                pre = cur;
            } else {
                dc++;
            }

            cur = cur.next;
        }

        // 最后一个节点如果等于 val 时，存在换尾的情况
        tail = pre;
        tail.next = null;

        return dc;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(head.toString());

        Node<T> node = head.next;
        while (node != null) {
            sb.append(" --> ").append(node.toString());
            node = node.next;
        }
        sb.append("]");

        return sb.toString();
    }

    public static void main(String[] args) {
        Code_01_LinkedList<Integer> ll = new Code_01_LinkedList<>();
        ll.addIfNotNull(1);
        printLL(ll);

        ll.addIfNotNull(2);
        printLL(ll);

        ll.addIfNotNull(3);
        printLL(ll);

        ll.addIfNotNull(4);
        printLL(ll);

        ll.addIfNotNull(5);
        printLL(ll);

        ll.revert();
        printLL(ll);

        System.out.println("****************************************************");
        Code_01_LinkedList<Integer> ll1 = new Code_01_LinkedList<>();
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(1);
        ll1.addIfNotNull(2);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(4);
        ll1.addIfNotNull(5);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(6);
        ll1.addIfNotNull(7);
        ll1.addIfNotNull(7);
        ll1.addIfNotNull(8);
        ll1.addIfNotNull(9);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(3);
        ll1.addIfNotNull(0);
        ll1.addIfNotNull(3);
        printLL(ll1);
        System.out.println("delete count: " + ll1.delete(3));
        printLL(ll1);
    }

    private static void printLL(Code_01_LinkedList ll) {
        System.out.println(ll);
        System.out.println("head: " + ll.head);
        System.out.println("tail: " + ll.tail);
        System.out.println("============");
    }

    private static class Node<T> {

        private T value;

        private Node<T> next;

        private Node(T val) {
            value = val;
        }

        @Override
        public String toString() {
            //return "(hashcode=" + Integer.toHexString(hashCode()) + ", value=" + value + ")";
            //return "value=" + value;
            return String.valueOf(value);
        }
    }
}
