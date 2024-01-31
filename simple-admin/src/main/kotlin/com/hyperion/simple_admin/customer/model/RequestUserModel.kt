package com.hyperion.simple_admin.customer.model

import jakarta.persistence.Column

data class RequestUserModel(
    val email: String,
    val password: String,
    val role: String,
    val name: String = "",
    val tel: String = "",
    val birthday: String = "",
)
