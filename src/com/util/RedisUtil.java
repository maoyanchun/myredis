package com.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by mycge on 2018/5/30.
 */
public class RedisUtil {
    private static String ip = "localhost"; //redis服务器地址
    private static int port = 6379; //redis服务器默认端口号
    private static int timeout = 10000; //从redis要连接的超时时间
    //private static String auth = "root"; //密码授权
    private static JedisPool pool = null;

    static {
        //连接池相关配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(1024);  //最大连接数
        config.setMaxIdle(200);  //最大空闲实例数
        config.setMaxWaitMillis(10000);  //等连接池给连接的最大时间，毫秒，设成-1表示永远不超时
        config.setTestOnBorrow(true);  //borrow一个实例的时候，是否提前进行validate操作

        pool = new JedisPool(config, ip, port, timeout);
    }

    //得到redis连接
    public synchronized static Jedis getJedis(){
        if(pool != null){
            return pool.getResource();
        }
        return null;
    }

    //关闭redis连接
    public static void close(final Jedis redis){
        if(redis != null){
            redis.close();
        }
    }

    private RedisUtil(){}

}
