package com.mikail.BookStoreApp.User

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/users")
@CrossOrigin
class UserController(@Autowired private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): User = userService.getUserById(id)

    @PostMapping
    fun createUser(@RequestBody user: User): User = userService.createUser(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody user: User): User = userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID) = userService.deleteUser(id)
}