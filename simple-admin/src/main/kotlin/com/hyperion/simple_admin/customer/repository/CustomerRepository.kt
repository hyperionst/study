package com.hyperion.simple_admin.customer.repository

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.toNonEmptyListOrNull
import com.hyperion.simple_admin.customer.model.BaseUserModel
import com.hyperion.simple_admin.customer.model.RequestUserModel
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class CustomerRepository(private val encoder: PasswordEncoder,
                         private val customerJdslRepository: CustomerJdslRepository) {

    /**
     * - 전체 데이터를 조회하고 NonEmptyList를 Either에 담아서 전달
     */
    suspend fun getCustomers(): Either<String, NonEmptyList<BaseUserModel?>> {
        return when (val actual = customerJdslRepository.findAll {
            select(
                entity(BaseUserModel::class),
            ).from(
                entity(BaseUserModel::class)
            )
        }.toNonEmptyListOrNull()) {
            null -> Either.Left("CustomerService.getCustomers :: No Data")
            else -> Either.Right(actual)
        }
    }

    /**
     * - id를 기준으로 데이터를 조회하고 NonEmptyList 를 Either 에 담아서 전달
     */
    suspend fun getCustomerById(id: Long): Either<String, NonEmptyList<BaseUserModel?>> {
        return when (val actual = customerJdslRepository.findAll {
            select(
                entity(BaseUserModel::class),
            ).from(
                entity(BaseUserModel::class)
            ).where(
                path(BaseUserModel::id).equal(id),
            )
        }.toNonEmptyListOrNull()) {
            null -> Either.Left("CustomerService.getCustomerById :: Not Found")
            else -> Either.Right(actual)
        }
    }


    /**
     * - 수신받은 entitiy를 업데이트.
     * @return Either<fail String,number of affected data>
     */
    suspend fun updateCustomerPassword(requestUserModel: RequestUserModel): Either<String, Int> {
        return when(val actual = customerJdslRepository.update {
            update(
                entity(BaseUserModel::class),
            ).set(
                path(BaseUserModel::password), requestUserModel.password
            ).where(
                path(BaseUserModel::email).equal(requestUserModel.email),
            )
        }){
            0 -> Either.Left("updateCustomerPassword :: There is no such data")
            else -> Either.Right(actual)
        }
    }


    /**
     * - id를 기준으로 Customer를 삭제, 결과를 Either로 전달
     * @return Either<fail String,number of affected data>
     */
    suspend fun deleteCustomer(id: Long): Either<String, Int> {
        return when (val actual = customerJdslRepository.delete() {
            deleteFrom(
                entity(BaseUserModel::class),
            ).where(
                path(BaseUserModel::id).equal(id)
            )
        }) {
            0 -> Either.Left("CustomerService.deleteCustomer :: Request Fail(There is No Such Data)")
            else -> Either.Right(actual)
        }
    }

    /**
     * - save 관련 exception을 Either로 catch한다.
     *
     * @throws IllegalArgumentException
     * @throws OptimisticLockingFailureException
     */
    suspend fun save(baseUserModel: BaseUserModel): Either<String, BaseUserModel> {
        return Either.catch {
            customerJdslRepository.save(baseUserModel)
        }.mapLeft {
            "CustomerService.save :: save fail"
        }
    }


    /**
     * Security UserDetailSevice전용
     * Sequrity에 corountine을 적용하지 않은 관계로인해 suspend를 적용하지 않음
     */
    fun getBaseUserModelForUserDetailService(email:String) : BaseUserModel? {
        return  customerJdslRepository.findAll {
            select(
                entity(BaseUserModel::class),
            ).from(
                entity(BaseUserModel::class)
            ).where(
                path(BaseUserModel::email).equal(email),
            )
        }.firstOrNull()
    }

}