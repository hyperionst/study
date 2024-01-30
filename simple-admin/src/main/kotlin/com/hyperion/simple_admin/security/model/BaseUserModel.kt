package com.hyperion.simple_admin.security.model

import java.util.*

data class BaseUserModel(
    val id: UUID,
    val email: String,
    val password: String,
    val role: Role,
    val name : String = "",
    val tel  : String = "",
    val birthday : String = "",
)

enum class Role {
    USER, ADMIN
}