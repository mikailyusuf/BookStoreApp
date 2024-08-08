package com.mikail.bookStoreApp.feature.auth

import com.mikail.bookStoreApp.feature.auth.dto.AuthenticationRequest
import com.mikail.bookStoreApp.feature.auth.dto.AuthenticationResponse
import com.mikail.bookStoreApp.feature.auth.dto.CreateUserRequest
import com.mikail.bookStoreApp.feature.auth.dto.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
class AuthController(@Autowired private val authService: AuthService) {

    @PostMapping("/signup")
    fun signup(
        @RequestPart("name") name: String,
        @RequestPart("email") email: String,
        @RequestPart("password") password: String,
        @RequestPart("avatar", required = false) avatar: MultipartFile
    ): UserResponse =
        authService.createUser(CreateUserRequest(name = name, email = email, password = password, avatar = avatar))

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse =
        authService.login(authRequest)

}




