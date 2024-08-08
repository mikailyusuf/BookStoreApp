package com.mikail.bookStoreApp.feature.auth.dto

import com.mikail.bookStoreApp.feature.user.User
import org.springframework.web.multipart.MultipartFile

data class CreateUserRequest(val name: String, val email: String, val password: String, val avatar: MultipartFile)

data class UserResponse(val email: String, val name: String, val avatar: String)

fun User.toUserResponse(): UserResponse {
    return UserResponse(email = email, avatar = avatar ?: "", name = name)
}


data class AuthenticationResponse(
    val accessToken: String
)

data class AuthenticationRequest(
    val email: String,
    val password: String,
)