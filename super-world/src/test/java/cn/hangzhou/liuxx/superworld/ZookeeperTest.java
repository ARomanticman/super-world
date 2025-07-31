package cn.hangzhou.liuxx.superworld;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.junit.jupiter.api.*;

public class ZookeeperTest {
    CuratorFramework client;
    @BeforeEach
    public void start() {
        client = CuratorFrameworkFactory.builder()
                .connectString("10.249.5.121:2181")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(10 * 1000)
                .retryPolicy(new RetryForever(1000))
                .build();
        client.start();
    }

    @Test
    public void create() throws Exception {
        String path = client.create().creatingParentsIfNeeded().forPath("/super/world/test", "saveTheWorld".getBytes());
        System.out.println(path);
    }

    @Test
    public void getData() throws Exception {
        byte[] bytes = client.getData().forPath("/super/world/test");
        System.out.println(new String(bytes));
    }

    @AfterEach
    public void close(){
        if(client != null){
            client.close();
        }
    }
}
