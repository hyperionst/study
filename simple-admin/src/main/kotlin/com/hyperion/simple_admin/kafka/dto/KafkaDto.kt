package com.hyperion.simple_admin.kafka.dto

import com.hyperion.simple_admin.customer.model.BaseUserModel

data class KafkaDto(val id : String, val message: BaseUserModel)
