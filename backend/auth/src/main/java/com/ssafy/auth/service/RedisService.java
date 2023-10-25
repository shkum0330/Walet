package com.ssafy.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate, RedisTemplate<String, Object> redisBlackListTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisBlackListTemplate = redisBlackListTemplate;
    }

    public void saveToken(String tokenKey, String tokenValue) {
        redisTemplate.opsForValue().set(tokenKey, tokenValue);
    }

    public void saveTokenWithExpiration(String tokenKey, String tokenValue, long expirationTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(tokenKey, tokenValue, expirationTime, timeUnit);
    }

    public String getToken(String tokenKey) {
        return (String) redisTemplate.opsForValue().get(tokenKey);
    }

    public void deleteToken(String tokenKey) {
        redisTemplate.delete(tokenKey);
    }

    public void setBlackList(String key, Object o, Long expiration) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisBlackListTemplate.opsForValue().set(key, o, expiration, TimeUnit.MILLISECONDS);
    }

    public boolean isBlackListed(String tokenKey) {
        return redisBlackListTemplate.hasKey(tokenKey);
    }
}
