package cn.xiaoyaoji.extension.cache.provider;

import cn.xiaoyaoji.core.util.JsonUtils;
import cn.xiaoyaoji.integration.cache.CacheProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zhoujingjie
 * @date 2016-07-28
 */
public class MemoryCacheProvider implements CacheProvider {
    private static Map<String, Item > dataMap;
    private static DelayQueue<Item> queue;

    static {
        queue = new DelayQueue<>();
        dataMap = new ConcurrentHashMap<>();
    }

    @Override
    public void put(String token, String key, Object data, int expires) {
        if (token == null) {
            return;
        }
        Item value = dataMap.get(token);
        if (value == null) {
            value = new Item(expires,token);
            dataMap.put(token, value);
        }
        if (!(data instanceof String)) {
            data = JsonUtils.toString(data);
        }
        value.data.put(key,data);
        queue.add(value);
    }

    @Override
    public Object get(String token, String key, int expires) {
        if (token == null) {
            return null;
        }
        Item value = dataMap.get(token);
        if (value == null) {
            return null;
        }
        value.setExpiresTime(expires);
        return value.data.get(key);
    }

    @Override
    public void remove(String table) {
        dataMap.remove(table);
    }

    @Override
    public void remove(String table, String key) {
        Item value = dataMap.get(table);
        if (value == null) {
            return;
        }
        value.data.remove(key);
    }

    private class Item implements Delayed {
        long expiresTime;
        String token;
        Map<String, Object> data = new ConcurrentHashMap<>();
        void setExpiresTime(int expiresTime){
            this.expiresTime = System.currentTimeMillis()+expiresTime*1000;
        }
        public Item(int expires, String token) {
            this.token = token;
            setExpiresTime(expires);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expiresTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
        }
    }

}
