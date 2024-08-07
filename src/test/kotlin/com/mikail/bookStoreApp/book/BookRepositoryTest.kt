package com.mikail.bookStoreApp.book

import com.mikail.bookStoreApp.user.User
import com.mikail.bookStoreApp.user.UserRepository
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
class BookRepositoryTest {

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `should save and retrieve book`() {
        val user = User(id = UUID.randomUUID(), name = "Jane Doe", email = "jane.doe@example.com", avatar = "https:www.image.com",createdAt = Instant.ofEpochMilli(234453334))
        val savedUser = userRepository.save(user)

        val book = Book(id = UUID.randomUUID(), title = "Spring Boot with Kotlin", author = "Author Name", coverImage = "https:www.cover.com", description = "Book Description" ,price = 29.99, createdAt = Instant.ofEpochMilli(234453334), user = savedUser)
        val savedBook = bookRepository.save(book)

        val retrievedBook = bookRepository.findById(savedBook.id).orElse(null)

        assertNotNull(retrievedBook)
        assertEquals("Spring Boot with Kotlin", retrievedBook.title)
        assertEquals("Author Name", retrievedBook.author)
        assertEquals(29.99, retrievedBook.price)
        assertEquals(savedUser.id, retrievedBook.user?.id)
    }
}