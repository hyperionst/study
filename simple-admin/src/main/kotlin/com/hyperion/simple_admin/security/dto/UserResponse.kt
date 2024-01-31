package com.hyperion.simple_admin.security.dto

import java.util.UUID

data class UserResponse(
    val uuid: Long,
    val email: String,
)