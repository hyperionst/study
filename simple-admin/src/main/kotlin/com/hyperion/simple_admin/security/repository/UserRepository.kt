package com.hyperion.simple_admin.security.repository


import com.hyperion.simple_admin.customer.model.BaseUserModel
import com.hyperion.simple_admin.customer.service.CustomerService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository



/**
 *  Security 연결포인트
 *  CustomerService 인자 자리에  findByEmail에
 *  user / password / role을 포함한 모델을 리턴받을수 있는
 *  repository / service 등을 주입받아서 처리한다.
 *
 *  userRepository -----> UserDetailService 구현체로 전달된다.
 *
 */
@Repository
class UserRepository(private val encoder: PasswordEncoder, private val customerService: CustomerService)  {

    fun findByEmail(email: String): BaseUserModel? =
        customerService.getBaseUserModelForUserDetailService(email)
}