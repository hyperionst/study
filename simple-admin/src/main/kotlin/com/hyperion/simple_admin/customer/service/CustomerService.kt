package com.hyperion.simple_admin.customer.service

import arrow.core.*


import com.hyperion.simple_admin.customer.model.BaseUserModel
import com.hyperion.simple_admin.customer.model.RequestUserModel
import com.hyperion.simple_admin.customer.repository.CustomerRepository
import com.hyperion.simple_admin.kafka.dto.KafkaDto
import com.hyperion.simple_admin.kafka.producer.Producer
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


/**
 * ## CustomerService
 *  - CustomerJdslRepository & Arrow (Either, NonEmptyList)
 *  - BaseUserModel에 대한 CRUD구현
 *  - password 인코딩이 필요하면 아래사용
 *      > encoder.encode(user.password)
 *
 *  @author KSJ / 240131
 */
//TODO : Either Error처리 Exception 구성 추가 할것
@Service
class CustomerService(
    private val encoder: PasswordEncoder,
    private val customerRepository: CustomerRepository,
    private val producer: Producer
) {

    /**
     * - 전체 데이터를 조회하고 NonEmptyList를 Either에 담아서 전달
     */
    suspend fun getCustomers(): Either<String, NonEmptyList<BaseUserModel?>> {
        return customerRepository.getCustomers()
    }


    /**
     * - id를 기준으로 데이터를 조회하고 NonEmptyList 를 Either 에 담아서 전달
     */
    suspend fun getCustomerById(id: Long): Either<String, NonEmptyList<BaseUserModel?>> {
        return customerRepository.getCustomerById(id)
    }


    /**
     * - 수신받은 entitiy를 업데이트.
     * @return Either<fail String,number of affected data>
     */
    suspend fun updateCustomerPassword(requestUserModel: RequestUserModel): Either<String, Int> {
        return customerRepository.updateCustomerPassword(requestUserModel)
    }

    /**
     * - id를 기준으로 Customer를 삭제, 결과를 Either로 전달
     * @return Either<fail String,number of affected data>
     */
    suspend fun deleteCustomer(id: Long): Either<String, Int> {
        return customerRepository.deleteCustomer(id)
    }


    /**
     * - save 관련 exception을 Either로 catch한다.
     * - 정상적으로 Save된 경우 Kafka로 데이터를 발행한다.
     *
     * @throws IllegalArgumentException
     * @throws OptimisticLockingFailureException
     */
    suspend fun save(requestUserModel: RequestUserModel): Either<String, BaseUserModel> {
        val reqToModel : (RequestUserModel) -> BaseUserModel = {data ->
            BaseUserModel(0, data.email, encoder.encode(data.password), data.role, data.name, data.tel, data.birthday)
        }

        val eitherUserModel =customerRepository.save(reqToModel(requestUserModel))
        eitherUserModel.onRight {
            producer.sendMessage(KafkaDto(requestUserModel.email, it))
        }
        return eitherUserModel
    }

    /**
     * Security UserDetailSevice전용
     * Sequrity에 corountine을 적용하지 않은 관계로인해 suspend를 적용하지 않음
     */
    fun getBaseUserModelForUserDetailService(email:String) : BaseUserModel? {
        return  customerRepository.getBaseUserModelForUserDetailService(email)
    }

}