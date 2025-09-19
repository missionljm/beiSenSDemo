package com.yonyou.beisendemo.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RedisClient {

    private final static Charset CODE = StandardCharsets.UTF_8;
    private final static String KEY_PREFIX = "MU_SMART_";
    private static RedisTemplate<String, String> redisTemplate;

    public static void register(RedisTemplate<String, String> redisTemplate) {
        RedisClient.redisTemplate = redisTemplate;
    }

    public static void checkNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException("edis argument can not be null!");
            }
        }
    }

    /**
     * 生成缓存key
     *
     * @param key
     * @return
     */
    public static byte[] toBytes(String key) {
        checkNull(key);
        key = KEY_PREFIX + key;
        return key.getBytes(CODE);
    }


    /**
     * 生成缓存value
     *
     * @param v
     * @param <T>
     * @return
     */
    public static <T> byte[] valBytes(T v) {
        if (v instanceof String) {
            return ((String) v).getBytes(CODE);
        } else {
            return JSONUtil.toJsonStr(v).getBytes(CODE);
        }
    }

    /**
     * 获取key对应的过期时间
     *
     * @param key
     * @return
     */
    public static Long ttl(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.ttl(toBytes(key)));
    }

    public static String getStr(String key) {
        return redisTemplate.execute((RedisCallback<String>) con -> {
            byte[] val = con.get(toBytes(key));
            return val == null ? "" : new String(val);
        });
    }

    public static void del(String key) {
        redisTemplate.execute((RedisCallback<Long>) con -> con.del(toBytes(key)));
    }

    /**
     * 设置缓存有效期
     *
     * @param key
     * @param expire
     * @return
     */
    public static Boolean expire(String key, Long expire) {
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.expire(toBytes(key), expire));
    }

    /**
     * 带缓存有效期的值写入
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public static Boolean setStrWithExpire(String key, String value, Long expire) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setEx(toBytes(key), expire, valBytes(value));
            }
        });
    }

    /**
     * 获取hash类型的数据
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> hGetAll(String key, Class<T> clazz) {
        Map<byte[], byte[]> records = redisTemplate.execute((RedisCallback<Map<byte[], byte[]>>) con -> con.hGetAll(toBytes(key)));
        if (CollectionUtil.isEmpty(records)) {
            return Collections.emptyMap();
        }
        Map<String, T> result = Maps.newHashMapWithExpectedSize(records.size());
        for (Map.Entry<byte[], byte[]> entry : records.entrySet()) {
            if (entry.getKey() == null) {
                continue;
            }
            result.put(new String(entry.getKey(), CODE), toObj(entry.getValue(), clazz));
        }
        return result;
    }

    public static <T> T hGet(String key, String fileId, Class<T> clazz) {
        byte[] record = redisTemplate.execute((RedisCallback<byte[]>) con -> con.hGet(toBytes(key), valBytes(fileId)));
        if (record == null) {
            return null;
        }
        return toObj(record, clazz);
    }

    private static <T> T toObj(byte[] bytes, Class<T> clazz) {
        if (bytes == null) {
            return null;
        }
        if (clazz == String.class) {
            return (T) new String(bytes, CODE);
        }
        return JSONUtil.toBean(new String(bytes, CODE), clazz);
    }

    /**
     * 自增
     *
     * @param key
     * @param fileId
     * @param cnt
     * @param <T>
     * @return
     */
    private static <T> Long hIncr(String key, String fileId, Integer cnt) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.hIncrBy(toBytes(key), valBytes(fileId), cnt));
    }

    /**
     * 删除hash值
     *
     * @param key
     * @param fileId
     * @return
     */
    private static Boolean hDel(String key, String fileId) {
        return redisTemplate.execute(((RedisCallback<Boolean>) con -> con.hDel(toBytes(key), valBytes(fileId)) > 0));
    }


    /**
     * 设置hash类型数据的字段值
     *
     * @param key    hash的key
     * @param fileId hash中的字段名
     * @param value  要设置的值
     * @return 操作是否成功
     */
    private static Boolean hSet(String key, String fileId, String value) {
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.hSet(toBytes(key), valBytes(fileId), valBytes(value)));
    }

    private static <T> void hMSet(String key, Map<String, T> hMap) {
        Map<byte[], byte[]> params = Maps.newHashMapWithExpectedSize(hMap.size());
        for (Map.Entry<String, T> entry : hMap.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            params.put(valBytes(entry.getKey()), valBytes(entry.getValue()));
        }
        redisTemplate.execute((RedisCallback<Object>) con -> {
            con.hMSet(toBytes(key), params);
            return null;
        });
    }

    /**
     * 批量获取hash值
     *
     * @param key
     * @param fileIds
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> Map<String, T> hMapGet(String key, final List<String> fileIds, Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<Map<String, T>>() {
            @Override
            public Map<String, T> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<String, T> result = Maps.newHashMapWithExpectedSize(fileIds.size());
                byte[][] f = new byte[fileIds.size()][];
                IntStream.range(0, fileIds.size()).forEach(i -> f[i] = valBytes(fileIds.get(i)));
                List<byte[]> bytes = connection.hMGet(toBytes(key), f);
                IntStream.range(0, fileIds.size()).forEach(i -> {
                    result.put(fileIds.get(i), toObj(bytes.get(i), clazz));
                });
                return result;
            }
        });
    }

    /**
     * 判断value是否在set中
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> Boolean sIsMember(String key, T value) {
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.sIsMember(toBytes(key), valBytes(value)));
    }

    public static <T> Set<T> sMembers(String key, Class<T> clazz) {
        return redisTemplate.execute((RedisCallback<Set<T>>) con -> {
            Set<byte[]> sets = con.sMembers(toBytes(key));
            if (CollectionUtil.isEmpty(sets)) {
                return Collections.emptySet();
            }
            return sets.stream().map(bytes -> toObj(bytes, clazz)).collect(Collectors.toSet());
        });
    }


    /**
     * 添加set值
     * @param key 键
     * @param value 值
     * @return
     * @param <T>
     */
    public static <T> Boolean sPut(String key, T value){
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.sAdd(toBytes(key), valBytes(value)) > 0);
    }


    /**
     * 移除set中的内容
     *
     * @param key
     * @param val
     * @param <T>
     */
    public static <T> void sDel(String key, T val) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.sRem(toBytes(key), valBytes(val));
                return null;
            }
        });
    }

    /**
     * 增加对应key的权重（zSet）
     * @param key
     * @param val
     * @param score
     * @return
     */
    public static Double zIncrBy(String key, String val, Double score){
        return redisTemplate.execute((RedisCallback<Double>) con -> con.zIncrBy(toBytes(key), score, valBytes(val)));
    }

    public static ImmutablePair<Integer, Double> zRankInfo(String key, String value) {
        double score = zScore(key, value);
        int rank = zRank(key, value);
        return ImmutablePair.of(rank, score);
    }

    /**
     * 获取分数
     *
     * @param key
     * @param value
     * @return
     */
    public static Double zScore(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zScore(toBytes(key), valBytes(value));
            }
        });
    }

    public static Integer zRank(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Integer>() {
            @Override
            public Integer doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zRank(toBytes(key), valBytes(value)).intValue();
            }
        });
    }

    /**
     * 找出排名靠前的n个
     *
     * @param key
     * @param n
     * @return
     */
    public static List<ImmutablePair<String, Double>> zTopNScore(String key, int n) {
        return redisTemplate.execute(new RedisCallback<List<ImmutablePair<String, Double>>>() {
            @Override
            public List<ImmutablePair<String, Double>> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<RedisZSetCommands.Tuple> set = connection.zRangeWithScores(toBytes(key), -n, -1);
                if (set == null) {
                    return Collections.emptyList();
                }
                return set.stream()
                        .map(tuple -> ImmutablePair.of(toObj(tuple.getValue(), String.class), tuple.getScore()))
                        .sorted((o1, o2) -> Double.compare(o2.getRight(), o1.getRight())).collect(Collectors.toList());
            }
        });
    }


    public static <T> Long lPush(String key, T val) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.lPush(toBytes(key), valBytes(val));
            }
        });
    }

    public static <T> Long rPush(String key, T val) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPush(toBytes(key), valBytes(val));
            }
        });
    }

    public static <T> List<T> lRange(String key, int start, int size, Class<T> clz) {
        return redisTemplate.execute(new RedisCallback<List<T>>() {

            @Override
            public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> list = connection.lRange(toBytes(key), start, size);
                if (CollectionUtils.isEmpty(list)) {
                    return new ArrayList<>();
                }
                return list.stream().map(k -> toObj(k, clz)).collect(Collectors.toList());
            }
        });
    }

    public static void lTrim(String key, int start, int size) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.lTrim(toBytes(key), start, size);
                return null;
            }
        });
    }



    /**
     * redis 管道执行的封装链路
     */
    public static class PipelineAction {
        private List<Runnable> run = new ArrayList<>();

        private RedisConnection connection;

        public PipelineAction add(String key, BiConsumer<RedisConnection, byte[]> conn) {
            run.add(() -> conn.accept(connection, RedisClient.toBytes(key)));
            return this;
        }

        public PipelineAction add(String key, String field, ThreeConsumer<RedisConnection, byte[], byte[]> conn) {
            run.add(() -> conn.accept(connection, RedisClient.toBytes(key), valBytes(field)));
            return this;
        }

        public void execute() {
            redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                PipelineAction.this.connection = connection;
                run.forEach(Runnable::run);
                return null;
            });
        }
    }

    @FunctionalInterface
    public interface ThreeConsumer<T, U, P> {
        void accept(T t, U u, P p);
    }


}
