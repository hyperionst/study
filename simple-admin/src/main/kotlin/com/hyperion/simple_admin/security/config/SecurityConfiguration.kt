package com.hyperion.simple_admin.security.config

import com.hyperion.simple_admin.security.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val authenticationProvider: AuthenticationProvider
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
//                    .requestMatchers("/api/auth", "api/auth/refresh", "/error")
//                    .permitAll()
//                    .requestMatchers(HttpMethod.POST, "/customer/save")
//                    .permitAll()
//                    .requestMatchers("/customer/**")
//                    .hasRole("ADMIN")
//                    .anyRequest()
//                    .fullyAuthenticated()

                    //MethodSecurity 사용중 - entry point는 열어주고 Method단위로 권한을 제한한다.
                    .requestMatchers("/api/auth", "api/auth/refresh", "/error")
                    .permitAll()
                    .requestMatchers("/api/article/**")
                    .permitAll()
                    .requestMatchers("/customer/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()

            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}