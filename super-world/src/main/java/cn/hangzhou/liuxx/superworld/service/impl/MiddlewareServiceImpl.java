package cn.hangzhou.liuxx.superworld.service.impl;

import cn.hangzhou.liuxx.superworld.service.MiddlewareService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MiddlewareServiceImpl implements MiddlewareService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addToRedis(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object getFromRedis(String key) {
        Object v = stringRedisTemplate.opsForValue().get(key);
        return v;
    }

    @Override
    public void testZk() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("")
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(10 * 1000)
                .retryPolicy(new RetryForever(1000))
                .build();
        client.start();

        

        client.close();
    }
}
