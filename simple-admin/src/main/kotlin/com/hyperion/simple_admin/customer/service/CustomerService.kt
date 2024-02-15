package com.hyperion.simple_admin.customer.service

import arrow.core.Either
import arrow.core.NonEmptyList
import com.hyperion.simple_admin.customer.model.BaseUserModel
import com.hyperion.simple_admin.customer.model.RequestUserModel
import com.hyperion.simple_admin.kafka.dto.KafkaDto

interface CustomerService {

    suspend fun getCustomers(): Either<String, NonEmptyList<BaseUserModel?>>

    suspend fun getCustomerById(id: Long): Either<String, BaseUserModel?>

    suspend fun updateCustomerPassword(id : Long, requestUserModel: RequestUserModel): Either<String, BaseUserModel?>

    suspend fun deleteCustomer(id: Long): Either<String, Int>

    suspend fun save(requestUserModel: RequestUserModel): Either<String, BaseUserModel>

    fun getBaseUserModelForUserDetailService(email:String) : BaseUserModel?
}