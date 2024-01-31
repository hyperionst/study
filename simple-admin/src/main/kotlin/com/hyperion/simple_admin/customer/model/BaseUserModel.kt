package com.hyperion.simple_admin.customer.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.util.*


/**
 *  ## BaseUserModel
 *   - Spring Security용 user-detail
 *   - Coustomer용 기초 사용자 정보
 *      > UUID를 기준으로 USER-DATA와 연결된다.
 *
 *  @author ksj
 */
@Entity
@Table(name = "customer")
data class BaseUserModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,

    @NotBlank
    @Column(name = "email", unique = true)
    val email: String,

    @NotBlank
    @Column(name = "password")
    val password: String,

    @Column(name = "role")
//    val role: Role,
    val role: String,

    @Column(name = "name")
    val name : String = "",

    @Column(name = "tel")
    val tel  : String = "",

    @Column(name = "birthday")
    val birthday : String = "",
)

enum class Role {
    USER, ADMIN
}