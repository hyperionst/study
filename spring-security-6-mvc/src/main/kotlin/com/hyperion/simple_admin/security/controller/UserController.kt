package com.hyperion.simple_admin.security.controller

import com.hyperion.simple_admin.security.dto.UserRequest
import com.hyperion.simple_admin.security.dto.UserResponse
import com.hyperion.simple_admin.security.model.BaseUserModel
import com.hyperion.simple_admin.security.model.Role
import com.hyperion.simple_admin.security.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): UserResponse =
        userService.createUser(userRequest.toModel())
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create user.")

    @GetMapping
    fun listAll(): List<UserResponse> = userService.findAll().map { it.toResponse() }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): UserResponse =
        userService.findByUUID(uuid)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")

    @DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID): ResponseEntity<Boolean> {
        val success = userService.deleteByUUID(uuid)
        return if (success)
            ResponseEntity.noContent()
                .build()
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
    }


    private fun BaseUserModel.toResponse(): UserResponse =   //클래스확장이용
        UserResponse(
            uuid = this.id,
            email = this.email,
        )

    private fun UserRequest.toModel(): BaseUserModel =   //클래스확장이용
        BaseUserModel(
            id = UUID.randomUUID(),
            email = this.email,
            password = this.password,
            role = Role.USER,
        )
}