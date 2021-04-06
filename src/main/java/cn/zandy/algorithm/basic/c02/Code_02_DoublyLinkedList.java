package cn.zandy.algorithm.basic.c02;

/**
 * 双向链表.
 *
 * 提供方法：
 * · 反转链表
 * · 删除链表中与给定值相等的节点
 * · 在头部添加节点
 * · 在尾部添加节点
 * · 获取头部元素，并删除
 * · 获取尾部元素，并删除
 */
public class Code_02_DoublyLinkedList<T> {

    private DNode<T> head;
    private DNode<T> tail;

    /**
     * 反转链表.
     */
    public void revert() {
        if (head == null || head == tail) {
            return;
        }

        // 上一个被反转节点 的指针
        DNode<T> pre = null;
        // 当前需要被反转节点 的指针
        DNode<T> cur = head;
        // 下一个将要被反转节点 的指针
        DNode<T> next;

        while (cur != null) {
            // 记录 下一个将要被反转的节点
            next = cur.next;

            // 【反转动作】
            cur.next = pre;
            cur.prev = next;

            // 反转之后，从新定义 pre 和 cur
            pre = cur;
            cur = next;
        }

        tail = head;
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
        head.prev = null;

        // 永远指向上一个不相等的节点
        DNode<T> pre = head;
        // 当前需要判断的节点
        DNode<T> cur = head.next;

        while (cur != null) {
            if (val.equals(cur.value)) {
                cur = cur.next;
                dc++;
                continue;
            }

            cur.prev = pre;
            pre.next = cur;

            pre = cur;
            cur = cur.next;
        }

        tail = pre;
        tail.next = null;

        return dc;
    }

    /**
     * 在头部添加节点.
     */
    public void addAtHead(T t) {
        if (t == null) {
            return;
        }

        DNode<T> newNode = new DNode<T>(t);
        if (head == null) {
            head = tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * 在尾部添加节点.
     */
    public void addAtTail(T t) {
        if (t == null) {
            return;
        }

        DNode<T> newNode = new DNode<T>(t);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    /**
     * 获取头部元素，并删除.
     */
    public DNode<T> pollFromHead() {
        if (head == null) {
            return null;
        }

        DNode<T> res = head;

        DNode<T> second = head.next;

        if (second != null) {
            head.next = null;
            second.prev = null;

            head = second;
        } else {
            head = tail = null;
        }

        return res;
    }

    /**
     * 获取尾部元素，并删除.
     */
    public DNode<T> pollFromTail() {
        if (head == null) {
            return null;
        }

        DNode<T> res = tail;

        DNode<T> secondToLast = tail.prev;

        if (secondToLast != null) {
            tail.prev = null;
            secondToLast.next = null;

            tail = secondToLast;
        } else {
            head = tail = null;
        }

        return res;
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[").append(head.toString());

        DNode<T> node = head.next;
        while (node != null) {
            sb.append(" <==> ").append(node.toString());
            node = node.next;
        }
        sb.append("]");

        return sb.toString();
    }

    public static void main(String[] args) {
        Code_02_DoublyLinkedList<Integer> dll_addhead = new Code_02_DoublyLinkedList<>();
        testAddAtHead(dll_addhead);
        testRevert(dll_addhead);
        testPollFromHead(dll_addhead);

        Code_02_DoublyLinkedList<Integer> dll_addtail = new Code_02_DoublyLinkedList<>();
        testAddAtTail(dll_addtail);
        testPollFromTail(dll_addtail);

        System.out.println("****************************************************");
        Code_02_DoublyLinkedList<Integer> dll = new Code_02_DoublyLinkedList<>();
        dll.addAtTail(3);
        dll.addAtTail(3);
        dll.addAtTail(1);
        dll.addAtTail(2);
        dll.addAtTail(3);
        dll.addAtTail(3);
        dll.addAtTail(3);
        dll.addAtTail(4);
        dll.addAtTail(5);
        dll.addAtTail(3);
        dll.addAtTail(3);
        dll.addAtTail(6);
        dll.addAtTail(7);
        dll.addAtTail(7);
        dll.addAtTail(8);
        dll.addAtTail(9);
        dll.addAtTail(3);
        dll.addAtTail(3);
        dll.addAtTail(0);
        dll.addAtTail(3);
        printDLL(dll);
        System.out.println("delete count: " + dll.delete(3));
        printDLL(dll);
    }

    private static void printDLL(Code_02_DoublyLinkedList dll) {
        System.out.println(dll);
        System.out.println("head: " + dll.head);
        System.out.println("tail: " + dll.tail);
        System.out.println("============");
    }

    private static class DNode<T> {

        private T value;

        /** 上一个节点指针 */
        private DNode<T> prev;

        /** 下一个节点指针 */
        private DNode<T> next;

        private DNode(T val) {
            value = val;
        }

        @Override
        public String toString() {
            //return "(hashcode=" + Integer.toHexString(hashCode()) + ", value=" + value + ")";
            //return "value=" + value;
            return String.valueOf(value);
        }
    }

    // -------------------------- for test -------------------------- //

    private static void testRevert(Code_02_DoublyLinkedList<Integer> dll) {
        System.out.println("============反转链表.============");
        dll.revert();
        printDLL(dll);
    }

    private static void testAddAtHead(Code_02_DoublyLinkedList<Integer> dll) {
        System.out.println("============在头部添加节点.============");
        for (Integer i = 1; i <= 5; i++) {
            dll.addAtHead(i);
            printDLL(dll);
        }
    }

    private static void testPollFromHead(Code_02_DoublyLinkedList<Integer> dll) {
        System.out.println("============获取头部元素，并删除.============");
        for (int i = 1; i <= 5; i++) {
            System.out.println("res: " + dll.pollFromHead());
            printDLL(dll);
        }
    }

    private static void testAddAtTail(Code_02_DoublyLinkedList<Integer> dll) {
        System.out.println("============在尾部添加节点.============");

        for (Integer i = 1; i <= 5; i++) {
            dll.addAtTail(i);
            printDLL(dll);
        }
    }

    private static void testPollFromTail(Code_02_DoublyLinkedList<Integer> dll) {
        System.out.println("============获取尾部元素，并删除.============");
        for (int i = 1; i <= 5; i++) {
            System.out.println("res: " + dll.pollFromTail());
            printDLL(dll);
        }
    }
}
