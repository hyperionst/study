package com.hyperion.simple_admin.kafka.consumer


import com.hyperion.simple_admin.kafka.dto.KafkaDto
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

/**
 * Kafka Consumer
 *
 *
 */
@Component
class Consumer {
    val log = KotlinLogging.logger {}

    //조심! susepend로 Coroutine 선언하면 에러가난다.  MessageConversion에서 에러가난다.
    @KafkaListener(topics = ["\${spring.kafka.template.default-topic}"], groupId = "\${spring.kafka.consumer.group-id}")
    fun consumeCustomer(@Payload message: KafkaDto, @Headers messageHeaders: MessageHeaders) {
        // 받은 객체를 가지고 로직 수행
        log.info("consumer: success >>> message: {}, headers: {}", message.toString(), messageHeaders)
    }
}