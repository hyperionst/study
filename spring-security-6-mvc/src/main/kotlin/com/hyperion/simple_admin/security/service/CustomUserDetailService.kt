package com.hyperion.simple_admin.security.service

import com.hyperion.simple_admin.security.model.BaseUserModel
import com.hyperion.simple_admin.security.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


/**

 * ## CustomUserDetailsService
 *  - UserDetailsService implementation
 *  - User Detail :
 *      > UserDetails represents the core user information.
 *       the default implementation called User, which holds the default information, such as username, password,
 *       and a collection of granted authorities(roles).
 *
 *  - UserDetailsService :
 *      > an interface used to load user-specific data.
 *       It is used by Spring Security to interact with our data source and validate users during authentication.
 *
 * 
 * @author 2024.01.30. KSJ
 */
@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")


    private fun BaseUserModel.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
}