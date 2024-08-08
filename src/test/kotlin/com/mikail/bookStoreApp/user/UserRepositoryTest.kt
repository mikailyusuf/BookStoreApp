package com.mikail.bookStoreApp.user

import com.mikail.bookStoreApp.feature.user.User
import com.mikail.bookStoreApp.feature.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.util.*
import kotlin.test.Test

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `should save and retrieve user`() {
        val user = User(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = "john.doe@example.com",
            avatar = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334)
        )
        val savedUser = userRepository.save(user)

        val retrievedUser = userRepository.findById(savedUser.id).orElse(null)

        assertNotNull(retrievedUser)
        assertEquals("John Doe", retrievedUser?.name)
        assertEquals("john.doe@example.com", retrievedUser?.email)
    }
}