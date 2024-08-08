package com.mikail.bookStoreApp.feature.auth

import com.mikail.bookStoreApp.feature.auth.dto.*
import com.mikail.bookStoreApp.feature.user.User
import com.mikail.bookStoreApp.feature.user.UserRepository
import com.mikail.bookStoreApp.services.CustomUserDetailsService
import com.mikail.bookStoreApp.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class AuthService(
    @Autowired private val userRepository: UserRepository,
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder
) {

    private val uploadDir: Path = Paths.get("uploads")

    init {
        Files.createDirectories(uploadDir)
    }

    fun createUser(request: CreateUserRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw DataIntegrityViolationException("Email already exists")
        }
        val imagePath = request.avatar.let {
            val uniqueFileName = generateUniqueFileName(it.originalFilename!!)
            val filePath = uploadDir.resolve(uniqueFileName)

            Files.copy(it.inputStream, filePath)
            uniqueFileName
        }
        return userRepository.save(
            User(
                email = request.email,
                name = request.name,
                avatar = imagePath,
                password = encoder.encode(request.password)
            )
        ).toUserResponse()
    }


    fun login(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )
        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)
        val accessToken = createAccessToken(user)
        return AuthenticationResponse(
            accessToken = accessToken,
        )
    }

    private fun generateUniqueFileName(originalFileName: String): String {
        val extension = originalFileName.substringAfterLast('.', "")
        val uniqueName = UUID.randomUUID().toString()
        return if (extension.isNotEmpty()) "$uniqueName.$extension" else uniqueName
    }

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + 3600000)
}