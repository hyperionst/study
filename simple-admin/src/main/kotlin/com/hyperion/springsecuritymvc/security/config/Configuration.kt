package com.hyperion.springsecuritymvc.security.config

import com.hyperion.springsecuritymvc.security.jwt.JwtProperties
import com.hyperion.springsecuritymvc.security.repository.UserRepository
import com.hyperion.springsecuritymvc.security.service.CustomUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


/**
 *  #Spring Security Config
 *  - !! must register the bean of type UserDetailsService,
 *  - !! register a PasswordEncoder. (Not USE PLAIN TEXT!!)
 *  - !! must provide the AuthenticationProvider and configure
 *
 *
 * @author KSJ
 */
@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    //UserDetailsService에 생성한 CustomUserDetailsService를 등록.
    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService = CustomUserDetailsService(userRepository)

    //Bcrypt 사용
    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()


    //AuthenticationProvider 최종 설정 및 등록설정구성
    @Bean
    fun authenticationProvider(userRepository: UserRepository): AuthenticationProvider =
        DaoAuthenticationProvider().also {
            it.setUserDetailsService(userDetailsService(userRepository))
            it.setPasswordEncoder(encoder())
        }

    //AuthenticationManager 설정
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

}