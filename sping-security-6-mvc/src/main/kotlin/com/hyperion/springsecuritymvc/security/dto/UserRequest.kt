package com.hyperion.springsecuritymvc.security.dto

data class UserRequest(
    val email: String,
    val password: String,
)