package com.hyperion.simple_admin.rediscache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


/**
 * properties와 spring starter class에 캐시를 적용한다고 알려주었다
 * Data를 가져오고 보낼 때, 도메인 모델을 Serialize 해 주기 위해 설정이 필요하다
 * 여기서 생성한 testCacheManger 객체를 앞으로 사용 할 레디스 어노테이션에 명시 해 주어야 한다
 */
@EnableCaching
@Configuration
class RedisCacheConfig {

    @Bean
    fun redisCacheManager(cf: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer<String>(StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer<Any>(
                    GenericJackson2JsonRedisSerializer()
                )
            )
            .entryTtl(Duration.ofMinutes(5L))

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf)
            .cacheDefaults(redisCacheConfiguration).build()
    }

}