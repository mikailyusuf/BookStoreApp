package com.mikail.bookStoreApp.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api")
@CrossOrigin
class UserController(
    @Autowired private val userService: UserService,

    ) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): User = userService.getUserById(id)

    @PostMapping("/signup")
    fun createUser(@RequestBody user: CreateUserRequest): UserResponse =
        userService.createUser(user.toUser()).toUserResponse()


    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthenticationRequest
    ): AuthenticationResponse =
        userService.login(authRequest)


    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody user: User): User = userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID) = userService.deleteUser(id)
}

data class TokenResponse(
    val token: String
)

data class RefreshTokenRequest(
    val token: String
)

data class AuthenticationResponse(
    val accessToken: String
)

data class AuthenticationRequest(
    val email: String,
    val password: String,
)


data class CreateUserRequest(val name: String, val email: String, val password: String, val avatar: String)

fun CreateUserRequest.toUser(): User {
    return User(email = email, avatar = avatar, name = name, password = password)
}

data class UserResponse(val email: String, val name: String, val avatar: String)

fun User.toUserResponse(): UserResponse {
    return UserResponse(email = email, avatar = avatar ?: "", name = name)
}
