package com.mikail.bookStoreApp.user

import com.mikail.bookStoreApp.services.CustomUserDetailsService
import com.mikail.bookStoreApp.services.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    @Autowired private val userRepository: UserRepository,
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder
) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: UUID): User = userRepository.findById(id).orElseThrow { Exception("User not found") }

    fun createUser(user: User): User = userRepository.save(user.copy(password = encoder.encode(user.password)))

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

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + 3600000)
}