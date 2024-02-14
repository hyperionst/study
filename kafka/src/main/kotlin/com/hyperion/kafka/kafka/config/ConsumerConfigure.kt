package com.hyperion.kafka.kafka.config

import com.hyperion.kafka.kafka.dto.KafkaEntity
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer


@Configuration
class ConsumerConfigure {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapAddress: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Bean
    fun pushEntityConsumerFactory(): ConsumerFactory<String, KafkaEntity> {
        val deserializer: JsonDeserializer<KafkaEntity> = gcmPushEntityJsonDeserializer()
        return DefaultKafkaConsumerFactory(
            consumerFactoryConfig(deserializer),
            StringDeserializer(),
            deserializer
        )
    }

    private fun consumerFactoryConfig(deserializer: JsonDeserializer<KafkaEntity>): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = deserializer
        return props
    }

    private fun gcmPushEntityJsonDeserializer(): JsonDeserializer<KafkaEntity> {
        val deserializer: JsonDeserializer<KafkaEntity> = JsonDeserializer(KafkaEntity::class.java)
        deserializer.setRemoveTypeHeaders(false)
        deserializer.addTrustedPackages("*")
        deserializer.setUseTypeMapperForKey(true)
        return deserializer
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, KafkaEntity> {
        val factory =
            ConcurrentKafkaListenerContainerFactory<String, KafkaEntity>()
        factory.consumerFactory = pushEntityConsumerFactory()
        return factory
    }
}