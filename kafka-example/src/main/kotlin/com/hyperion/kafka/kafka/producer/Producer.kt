package com.hyperion.kafka.kafka.producer

import com.hyperion.kafka.kafka.dto.KafkaEntity
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
class Producer(val kafkaTemplate: KafkaTemplate<String, KafkaEntity>) {
    val log = KotlinLogging.logger {}

    @Value("\${spring.kafka.template.default-topic}")
    lateinit var topicName: String

    fun sendMessage(kafkaEntity: KafkaEntity) {
        val message: Message<KafkaEntity> = MessageBuilder
            .withPayload<KafkaEntity>(kafkaEntity)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .build()

        val future: CompletableFuture<SendResult<String?, KafkaEntity>> = kafkaTemplate.send(message)

        future.whenComplete { result: SendResult<String?, KafkaEntity>, ex: Throwable? ->
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