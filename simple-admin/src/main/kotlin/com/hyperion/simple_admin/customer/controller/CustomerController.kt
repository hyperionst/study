package com.hyperion.simple_admin.customer.controller

import arrow.core.Either
import arrow.core.NonEmptyList
import com.hyperion.core.logger
import com.hyperion.simple_admin.customer.model.BaseUserModel
import com.hyperion.simple_admin.customer.model.RequestUserModel
import com.hyperion.simple_admin.customer.service.CustomerService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * ## CustomerController
 * - BaseUserModel에 관련된 Request를 처리
 * - MethodSecurity로 권한 제어
 *
 */
//TODO : RestControllerAdvice로 에러처리를 묶어서 하는 방법 고려
@RestController
@RequestMapping("/customer")
class CustomerController(private val customerService: CustomerService,) {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    suspend fun getCustomers() : ResponseEntity<NonEmptyList<BaseUserModel?>> {
        return when(val value = customerService.getCustomers()){
            is Either.Left -> {
                logger.info { value.value }
                ResponseEntity.noContent().build()
            }
            is Either.Right ->{
                ResponseEntity.ok().body(value.value)
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    suspend fun getCustomer(@PathVariable id : Long) : ResponseEntity<BaseUserModel?> {
        return when(val value = customerService.getCustomerById(id)){
            is Either.Left -> {
                logger.info { value.value }
                ResponseEntity.noContent().build()
            }
            is Either.Right ->{
                ResponseEntity.ok().body(value.value)
            }
        }
    }


    @PostMapping("/save")
    suspend fun saveCustomers(@Valid  @RequestBody requestUserModel: RequestUserModel) : ResponseEntity<BaseUserModel> {
        return when(val value = customerService.save(requestUserModel)){
            is Either.Left -> {
                logger.info { value.value }
                ResponseEntity.noContent().build()
            }
            is Either.Right ->{
                ResponseEntity.ok().body(value.value)
            }
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/update/{id}")
    suspend fun updateCustomerPassword(@PathVariable id: Long, @Valid @RequestBody requestUserModel: RequestUserModel) : ResponseEntity<BaseUserModel?> {
        return when(val value = customerService.updateCustomerPassword(id, requestUserModel)){
            is Either.Left -> {
                logger.info { value.value }
                ResponseEntity.noContent().build()
            }
            is Either.Right ->{
                ResponseEntity.ok().body(value.value)
            }
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    suspend fun deleteCustomer(@PathVariable id: Long) : ResponseEntity<String> {
        return when(val value = customerService.deleteCustomer(id)){
            is Either.Left -> {
                logger.info { value.value }
                ResponseEntity.noContent().build()
            }
            is Either.Right ->{
                ResponseEntity.ok().body("deleted:${value.value}")
            }
        }
    }
}