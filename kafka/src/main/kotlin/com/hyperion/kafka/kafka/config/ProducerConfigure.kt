package com.hyperion.kafka.kafka.config

import org.springframework.kafka.support.serializer.JsonSerializer
import com.hyperion.kafka.kafka.dto.KafkaEntity
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
class ProducerConfigure {

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapAddress: String

    @Bean
    fun producerFactory(): ProducerFactory<String, KafkaEntity> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory<String, KafkaEntity>(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, KafkaEntity> {
        return KafkaTemplate(producerFactory())
    }
}