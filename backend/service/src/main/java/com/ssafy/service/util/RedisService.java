package com.ssafy.service.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getKey(){
        return (String) redisTemplate.opsForValue().get("nhKey");
    }

    public void saveKey(String key){
        redisTemplate.opsForValue().set("nhKey",key,7_000_000_000L, TimeUnit.MILLISECONDS);
    }
}
