package com.hyperion.kafka

import com.hyperion.kafka.kafka.dto.KafkaEntity
import com.hyperion.kafka.kafka.producer.Producer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(val producer: Producer) {

    @PostMapping("/kafka")
    fun sendMessage(@RequestBody message: KafkaEntity): String {
        producer.sendMessage(message)
        return "ok"
    }
}