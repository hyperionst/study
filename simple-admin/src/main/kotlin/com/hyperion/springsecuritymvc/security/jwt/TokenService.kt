package com.hyperion.springsecuritymvc.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TokenService(jwtProperties: JwtProperties) {

    //지정된 property 에서 키값을 가저와서 HMAC-SHA 적용
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())

    
    //generate function is responsible for creating a serialized, URL-safe String with JWT tokens
    //JWT - 토큰 내부 내용을 구성하여 생성 Builder는 JWT구조 형태와 같은 순서다.
    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)            
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    
    //유효한지 확인
    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return userDetails.username == email && !isExpired(token)
    }

    
    // Email 주소만 받아온다.
    fun extractEmail(token: String): String? = getAllClaims(token).subject
    //토큰 유효기간을 확인한다.
    
    fun isExpired(token: String): Boolean = getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))
    //claim 전체를 얻어온다.
    
    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser().verifyWith(secretKey).build()
        return parser.parseSignedClaims(token).payload
    }
}