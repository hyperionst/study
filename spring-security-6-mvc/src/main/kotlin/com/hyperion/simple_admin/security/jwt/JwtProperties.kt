package com.hyperion.simple_admin.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("jwt")  //application.property에서 받아옴
data class JwtProperties(
    val key: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long,
)