package com.ssafy.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public TokenRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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
}
