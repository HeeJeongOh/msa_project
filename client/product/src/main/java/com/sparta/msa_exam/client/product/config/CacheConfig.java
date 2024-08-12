package com.sparta.msa_exam.client.product.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {
    @Bean
    public RedisCacheManager cacheManager(
            RedisConnectionFactory redisConnectionFactory
    ){
        // 레디스를 통해 스프링 캐시 활용
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofSeconds(60))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.java()));

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
