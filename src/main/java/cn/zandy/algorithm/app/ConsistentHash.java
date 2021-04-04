package cn.zandy.algorithm.app;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

/**
 * Java 实现一致性哈希算法.
 */
public class ConsistentHash {

    /** 所有真实的服务节点 */
    private final String[] servers;

    /** 为了使服务节点均匀分布，增加一些虚拟节点，此常量表示每个真实服务节点对应的虚拟节点个数 */
    private static final int VIRTUAL_NODE_NUM = 3;

    /**
     * 存放所有节点的映射表：
     * · kEY: 节点虚拟后的哈希值
     * · VAL: 真实服务节点(ip:port)
     *
     * <pre>
     * 形如：
     * {
     *   477217617  = 127.0.0.1:6378,
     *   547570365  = 127.0.0.1:6377,
     *   1324703208 = 127.0.0.1:6379,
     *   2203684144 = 127.0.0.1:6377,
     *   2507390694 = 127.0.0.1:6377,
     *   3205615763 = 127.0.0.1:6378,
     *   3369515486 = 127.0.0.1:6378,
     *   3538772667 = 127.0.0.1:6379,
     *   4290510647 = 127.0.0.1:6379
     * }
     * </pre>
     */
    private SortedMap<Long, String> virtualNodeMap = new TreeMap<>();

    public ConsistentHash(String[] servers) {
        this.servers = servers;

        for (String server : servers) {
            addVirtualNodes(server);
        }
    }

    private void addVirtualNodes(String server) {
        for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
            long hashCode = hash(server + ":" + i);
            virtualNodeMap.put(hashCode, server);
        }
    }

    /**
     * hash 函数可以使用 md5, sha-1, sha-256 等
     * 虽然 md5, sha-1 哈希算法在签名领域已经不再安全，但运算速度比较快，在非安全领域是可以使用的.
     */
    private long hash(String key) {
        String md5key = md5Hex(key);
        return Long.parseLong(md5key.substring(0, 15), 16) % ((long) Math.pow(2, 32));
    }

    /**
     * 为用户指定的 key 分配 server 节点, 分配规则:
     *
     * 假设用户传入的 key 的哈希值是 hk, 那么哈希值【大于或等于】hk 的第一个节点，就是我们要分配的 server 节点；
     * 如果都小于 hk, 则分配 sortedMap 中第一个节点.
     */
    private String getServer(String key) {
        long hk = hash(key);

        SortedMap<Long, String> tailMap = virtualNodeMap.tailMap(hk);
        long serverKey = tailMap.isEmpty() ? virtualNodeMap.firstKey() : tailMap.firstKey();

        return virtualNodeMap.get(serverKey);
    }

    public static void main(String[] args) {
        System.out.println("2的32次幂: " + ((long) Math.pow(2, 32)));
        System.out.println("2的32次幂: " + (1L << 32));
        String md5_32 = md5Hex("127.0.0.1:6377:1");
        String md5_15 = md5_32.substring(0, 15); // 为了下面转成16进制数（15位16进制 -> 60位二进制，不会溢出）
        System.out.println("md5_32: " + md5_32 + ", len=" + md5_32.length());
        System.out.println("md5_15: " + md5_15 + ", len=" + md5_15.length());
        System.out.println("16进制: " + Long.parseLong(md5_15, 16)); // 2^59 ~ 2^60-1
        System.out.println(Long.parseLong(md5_15, 16) % (1L << 32));
        System.out.println((1L << 59) % (1L << 32));
        System.out.println(((1L << 60) - 1) % (1L << 32));
        System.out.println("--------------------------");

        String[] servers = {"127.0.0.1:6377", "127.0.0.1:6378", "127.0.0.1:6379"};
        ConsistentHash ch = new ConsistentHash(servers);
        System.out.println(ch.virtualNodeMap);
        System.out.println(ch.getServer("locking:8260:1"));
        System.out.println(ch.getServer("locking:8260:2"));
    }
}
