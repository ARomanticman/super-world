package cn.hangzhou.liuxx.superworld.utils;


import com.alibaba.fastjson2.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CacheableClientManager<T> {

    public Map<String, T> clients = new ConcurrentHashMap<>();

    public abstract String cacheKey(JSONObject config);

    public T get(JSONObject config) {
        String cacheKey = cacheKey(config);
        if (clients.get(cacheKey) == null) {
            T client = initClient(config);
            clients.put(cacheKey, client);
            return client;
        } else {
            T cachedClient = clients.get(cacheKey(config));
            if (validate(cachedClient, config)) {
                return cachedClient;
            } else {
                T client = initClient(config);
                clients.put(cacheKey, client);
                return client;
            }
        }
    }

    public boolean validate(T client, JSONObject config) {
        return true;
    }

    public int size() {
        return clients.size();
    }

    public abstract T initClient(JSONObject config);
}
