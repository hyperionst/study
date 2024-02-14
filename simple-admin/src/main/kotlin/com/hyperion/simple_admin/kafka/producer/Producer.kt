package com.hyperion.simple_admin.kafka.producer

import com.hyperion.simple_admin.kafka.dto.KafkaDto
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture


@Component
class Producer(val kafkaTemplate: KafkaTemplate<String, KafkaDto>) {
    val log = KotlinLogging.logger {}

    @Value("\${spring.kafka.template.default-topic}")
    lateinit var topicName: String

    suspend fun sendMessage(kafkaDto: KafkaDto) {
        val message: Message<KafkaDto> = MessageBuilder
            .withPayload<KafkaDto>(kafkaDto)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .build()

        val future: CompletableFuture<SendResult<String?, KafkaDto>> = kafkaTemplate.send(message)

        future.whenComplete { result: SendResult<String?, KafkaDto>, ex: Throwable? ->
            if (ex == null) {
                log.info(
                    "producer: success >>> message: {}, offset: {}",
                    result.producerRecord.value().toString(), result.recordMetadata.offset()
                )
            } else {
                log.info("producer: failure >>> message: {}", ex.message)
            }
        }
    }
}