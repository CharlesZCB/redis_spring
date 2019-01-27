package com.cn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisJavaApplicationTests {

    @Test
    public void contextLoads() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.1.103");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());


        //字符串
        jedis.set("name","张三");
        String name=jedis.get("name");
        System.out.println(name);

        //列表
        jedis.lpush("province","江苏");
        jedis.lpush("province","浙江");
        jedis.lpush("province","广东");

        List<String> list = jedis.lrange("province",0,Integer.MAX_VALUE);
        list.forEach(p-> System.out.println(p));

    }


    @Test
    public void test01(){
        Jedis jedis = new Jedis("192.168.1.103");
        for(int i  = 0;i<100000;i++){
            jedis.lpush("num",String.valueOf(i));
        }
    }

    @Test
    public void test02(){
        Jedis jedis = new Jedis("192.168.1.103");
        List<String> list = jedis.lrange("num",0,20);
        list.forEach(number-> System.out.println(number));
    }

    @Test
    public void test03(){
        Jedis jedis = new Jedis("192.168.1.103");
        Long lon = jedis.bitcount("province");
        System.out.println(lon);
    }

    @Test
    public void test05(){
        Jedis jedis = new Jedis("192.168.1.103");
        jedis.hset("code","age","12");
        jedis.hset("code","age","23");//这个会覆盖上面的12
        jedis.hset("code","sex","男");
        jedis.hset("code","address","江苏省连云港市东海县黄川镇张桥村11-16号");

        jedis.hset("code1","age","23");//这个会覆盖上面的12
        jedis.hset("code1","sex","男");
        jedis.hset("code1","address","江苏省连云港市东海县黄川镇张桥村11-16号");

        String age=jedis.hget("code","age");
        System.out.println(age);



    }

    /**
     * //有序添加
     */
    @Test
    public void test06(){
        Jedis jedis = new Jedis("192.168.1.103");
        jedis.zadd("score",98.8,"张三");
        jedis.zadd("score",99,"黎明");
        System.out.println(jedis.zrange("score",0,Integer.MAX_VALUE));
        jedis.publish("score","This is message.");

    }


}

