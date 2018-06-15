package com.test;

import com.util.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        //testString();

        //testList();

        //testSet();

        testHash();
    }

    public static void testHash(){
        Jedis redis = RedisUtil.getJedis();
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("username", "zhangsan");
        myMap.put("password", "123");
        myMap.put("school", "北大");

        redis.hmset("myMap",myMap);
        Map<String, String> userMap = redis.hgetAll("myMap");
        for(Map.Entry<String, String> item: userMap.entrySet()){
            System.out.println(item.getKey()+" : "+item.getValue());
        }

        //删除某个键
        redis.hdel("myMap", "school");
        System.out.println(redis.hmget("myMap", "school"));
        System.out.println(redis.hlen("myMap"));
        System.out.println(redis.exists("myMap"));

        System.out.println(redis.hkeys("myMap"));

        RedisUtil.close(redis);
    }

    public static void getKeys(){
        Jedis redis = RedisUtil.getJedis();

        Set<String> keyList = redis.keys("*");
        for(String key: keyList){
            System.out.println(key);
        }
        RedisUtil.close(redis);
    }

    public static void testSet(){
        Jedis redis = RedisUtil.getJedis();
        redis.sadd("myset","橘子");
        redis.sadd("myset","香蕉");
        redis.sadd("myset","苹果");
        redis.sadd("myset","苹果");
        redis.sadd("myset","草莓");
        redis.sadd("myset","草莓");
        redis.sadd("myset","猕猴桃");

        redis.srem("myset","草莓");
        System.out.println(redis.smembers("myset"));

        System.out.println(redis.sismember("myset", "猕猴桃"));

        System.out.println(redis.srandmember("myset"));
        System.out.println(redis.scard("myset"));

        RedisUtil.close(redis);
    }

    public static void testList(){
        Jedis redis = RedisUtil.getJedis();
        redis.del("mylist");
        redis.lpush("mylist", "aaa", "bbb", "ccc");
        redis.lpush("mylist", "fff");

        System.out.println(redis.lrange("mylist", 0, -1));

        redis.del("a");
        redis.lpush("a","1","5","3","2","4");//元素是可以转成double类型的
        SortingParams param = new SortingParams();
        System.out.println(redis.sort("a", param.desc()));

        redis.del("mylist");

        redis.rpush("mylist","aaa");
        redis.rpush("mylist","bbb");
        redis.rpush("mylist","ccc");
        redis.rpush("mylist","fff");
        System.out.println(redis.lrange("mylist", 0, -1));

        RedisUtil.close(redis);
    }

     public static void testString(){
        Jedis redis = RedisUtil.getJedis();
        //System.out.println("redis服务连接成功！"+ redis.ping());
        redis.set("username", "哈哈好啊");  //命令行取的时候乱码：reids-cli --raw
        redis.append("username", "remarks");
        redis.mset("username", "zhangsan", "password","666666", "remark","2018-08-30");

        redis.incr("age");

        System.out.println(redis.get("username"));
        System.out.println(redis.get("password"));
        System.out.println(redis.get("age"));

        RedisUtil.close(redis);
    }
}
