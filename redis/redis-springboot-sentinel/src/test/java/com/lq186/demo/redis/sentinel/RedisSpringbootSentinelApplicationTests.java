package com.lq186.demo.redis.sentinel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSpringbootSentinelApplicationTests {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSetAndGet() {
        final String key = "demo.springboot.redis.test";
        redisTemplate.opsForValue().set(key, "This is redis sentinel test.", 300, TimeUnit.SECONDS);

        final String value = (String) redisTemplate.opsForValue().get(key);
        System.out.printf(">> the value is: %s \n", value);
    }
}
