package com.mikail.bookStoreApp.feature.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    @Autowired private val userRepository: UserRepository
) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: UUID): User = userRepository.findById(id).orElseThrow { Exception("User not found") }

    fun updateUser(id: UUID, userDetails: User): User {
        val user = getUserById(id)
        return userRepository.save(
            user.copy(
                name = userDetails.name,
                email = userDetails.email,
                avatar = userDetails.avatar
            )
        )
    }

    fun deleteUser(id: UUID) = userRepository.deleteById(id)


}