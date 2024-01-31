package com.hyperion.simple_admin.security.repository

import com.hyperion.simple_admin.customer.model.Role
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



//--------------------------------------------------Security TEST code-------------------------------------
//    private val users = mutableSetOf(
//        BaseUserModel(
//            id = 0,
//            email = "email-1@gmail.com",
//            password = encoder.encode("pass1"),
//            role = "USER"//Role.USER,
//        ),
//        BaseUserModel(
//            id = 1,
//            email = "email-2@gmail.com",
//            password = encoder.encode("pass1"),
//            role = "ADMIN" //Role.ADMIN,
//        ),
//        BaseUserModel(
//            id = 2,
//            email = "email-3@gmail.com",
//            password = encoder.encode("pass1"),
//            role = "USER" //Role.USER,
//        ),BaseUserModel(
//            id = 6,
//            email = "222@aaa.com",
//            password = encoder.encode("222"),
//            role = "USER" //Role.USER,
//        ),
//    )
//    fun save(user: BaseUserModel): Boolean {
//        val updated = user.copy(password = encoder.encode(user.password))
//        return users.add(updated)
//    }
//
//    fun findByEmail(email: String): BaseUserModel? = users.firstOrNull { it.email == email }


//-------------------------------------------------------------------------------------
//    fun findAll(): Set<BaseUserModel> = users
//
//    fun findByUUID(uuid: Long): BaseUserModel? = users.firstOrNull { it.id == uuid }
//
//
//    fun deleteByUUID(uuid: Long): Boolean {
//        val foundUser = findByUUID(uuid)
//        return foundUser?.let {
//            users.removeIf {
//                it.id == uuid
//            }
//        } ?: false
//    }
}