package com.mikail.bookStoreApp.user


import com.mikail.bookStoreApp.feature.user.User
import com.mikail.bookStoreApp.feature.user.UserRepository
import com.mikail.bookStoreApp.feature.user.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun `should get all users`() {
        val users = listOf(
            User(
                id = UUID.randomUUID(), name = "User 1", email = "user1@example.com", avatar = "https:www.image.com",
                createdAt = Instant.ofEpochMilli(234453334)
            ),
            User(
                id = UUID.randomUUID(), name = "User 2", email = "user2@example.com", avatar = "https:www.image.com",
                createdAt = Instant.ofEpochMilli(234453334)
            )
        )
        given(userRepository.findAll()).willReturn(users)

        val result = userService.getAllUsers()

        assertEquals(2, result.size)
    }

    @Test
    fun `should get user by id`() {
        val id = UUID.randomUUID()
        val user = User(
            id = id, name = "User", email = "user@example.com", avatar = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334)
        )
        given(userRepository.findById(id)).willReturn(Optional.of(user))

        val result = userService.getUserById(id)

        assertNotNull(result)
        assertEquals(user.name, result.name)
    }

    @Test
    fun `should create user`() {
        val user = User(
            id = UUID.randomUUID(), name = "New User", email = "new.user@example.com", avatar = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334)
        )

        given(userRepository.save(Mockito.any(User::class.java))).willReturn(user)

        val result = userService.createUser(user)

        assertNotNull(result)
        assertEquals(user.name, result.name)
    }

    @Test
    fun `should update user`() {
        val id = UUID.randomUUID()
        val user = User(
            id = id, name = "Old Name", email = "old@example.com", avatar = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334)
        )
        val updatedUserDetails = User(
            id = id, name = "New Name", email = "new@example.com", avatar = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334)
        )

        given(userRepository.findById(id)).willReturn(Optional.of(user))
        given(userRepository.save(Mockito.any(User::class.java))).willReturn(updatedUserDetails)

        val result = userService.updateUser(id, updatedUserDetails)

        assertNotNull(result)
        assertEquals(updatedUserDetails.name, result.name)
        assertEquals(updatedUserDetails.email, result.email)
    }

    @Test
    fun `should delete user`() {
        val id = UUID.randomUUID()
        Mockito.doNothing().`when`(userRepository).deleteById(id)

        userService.deleteUser(id)

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(id)
    }
}
