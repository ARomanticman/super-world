package cn.hangzhou.liuxx.superworld.service;

public interface MiddlewareService {

    void addToRedis(String key, String value);

    Object getFromRedis(String key);

    void testZk();
}
