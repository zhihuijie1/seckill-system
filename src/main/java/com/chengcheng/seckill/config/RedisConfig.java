package com.chengcheng.seckill.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import
        org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型 key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //Hash类型 value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}

/**
 * RedisConfig 它的主要功能是配置 RedisTemplate，用于与 Redis 数据库进行交互。
 * 为什么要进行序列化？
 * <p>
 * 1：持久化存储：Redis 可以将数据持久化到磁盘，序列化是将内存中的数据转换为可以持久化存储的形式，以便重启后可以重新加载数据。
 * <p>
 * 2：网络传输：Redis 通常用作缓存服务器，用于存储和提供数据。当数据通过网络传输时，需要将数据序列化为字节流以便于传输，并在接收端进行反序列化。
 * <p>
 * 使用了 GenericJackson2JsonRedisSerializer 对象进行序列化，
 * 它将对象序列化为 JSON 格式。这种序列化方式具有通用性，可以序列化各种类型的对象，
 * 并且 JSON 是一种轻量级的数据交换格式，在网络传输和持久化存储时效率较高。
 */
