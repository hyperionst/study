package com.hyperion.kafka.kafka.consumer

import com.hyperion.kafka.kafka.dto.KafkaEntity
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class Consumer {
    val log = KotlinLogging.logger {}

    @KafkaListener(topics = ["\${spring.kafka.template.default-topic}"], groupId = "\${spring.kafka.consumer.group-id}")
    fun consume(
        @Payload message: KafkaEntity,
        @Headers messageHeaders: MessageHeaders?
    ) {
        // 받은 객체를 가지고 로직 수행
        log.info("consumer: success >>> message: {}, headers: {}", message.toString(), messageHeaders)
    }
}