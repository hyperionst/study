package com.hyperion.simple_admin.security.repository

import com.hyperion.simple_admin.security.model.BaseUserModel
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(private val encoder: PasswordEncoder) {
    private val users = mutableSetOf(
        BaseUserModel(
            id = UUID.randomUUID(),
            email = "email-1@gmail.com",
            password = encoder.encode("pass1"),
            role = "USER"
        ),
        BaseUserModel(
            id = UUID.randomUUID(),
            email = "111@aaa.com",
            password = encoder.encode("111"),
            role = "ADMIN"
        ),
        BaseUserModel(
            id = UUID.randomUUID(),
            email = "222@aaa.com",
            password = encoder.encode("222"),
            role = "USER",
        ),
    )


    fun findByEmail(email: String): BaseUserModel? = users.firstOrNull { it.email == email }

    fun save(user: BaseUserModel): Boolean {
        val updated = user.copy(password = encoder.encode(user.password))
        return users.add(updated)
    }

    fun findAll(): Set<BaseUserModel> = users

    fun findByUUID(uuid: UUID): BaseUserModel? = users.firstOrNull { it.id == uuid }


    fun deleteByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid)
        return foundUser?.let {
            users.removeIf {
                it.id == uuid
            }
        } ?: false
    }
}