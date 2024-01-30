package com.hyperion.springsecuritymvc.security.service


import com.hyperion.springsecuritymvc.security.model.BaseUserModel
import com.hyperion.springsecuritymvc.security.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*
@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: BaseUserModel): BaseUserModel? {
        val found = userRepository.findByEmail(user.email)
        return if (found == null) {
            userRepository.save(user)
            user
        } else null
    }


    fun findByUUID(uuid: UUID): BaseUserModel? = userRepository.findByUUID(uuid)


    fun findAll(): List<BaseUserModel> = userRepository.findAll().toList()


    fun deleteByUUID(uuid: UUID): Boolean = userRepository.deleteByUUID(uuid)
}