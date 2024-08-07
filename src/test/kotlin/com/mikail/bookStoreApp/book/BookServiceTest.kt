package com.mikail.bookStoreApp.book

import com.mikail.bookStoreApp.user.User
import com.mikail.bookStoreApp.user.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant
import java.util.*
import kotlin.test.Test

@ExtendWith(SpringExtension::class)
@SpringBootTest
class BookServiceTest {

    @Autowired
    private lateinit var bookService: BookService

    @MockBean
    private lateinit var bookRepository: BookRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun `should get all books`() {
        val books = listOf(
            Book(
                id = UUID.randomUUID(),
                title = "Book 1",
                author = "Author 1",
                price = 10.0,
                description = "Book Description",
                coverImage = "https:www.image.com",
                createdAt = Instant.ofEpochMilli(234453334),
                user = null
            ),
            Book(
                id = UUID.randomUUID(),
                title = "Book 2",
                author = "Author 2",
                price = 15.0,
                description = "Book Description",
                coverImage = "https:www.image.com",
                createdAt = Instant.ofEpochMilli(234453334),
                user = null
            )
        )
        given(bookRepository.findAll()).willReturn(books)

        val result = bookService.getAllBooks()

        assertEquals(2, result.size)
    }

    @Test
    fun `should get book by id`() {
        val id = UUID.randomUUID()
        val book = Book(
            id = id, title = "Book", author = "Author", price = 20.0, description = "Book Description",
            coverImage = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334), user = null
        )
        given(bookRepository.findById(id)).willReturn(Optional.of(book))

        val result = bookService.getBookById(id)

        assertNotNull(result)
        assertEquals(book.title, result.title)
    }

    @Test
    fun `should create book`() {
        val userId = UUID.randomUUID()
        val user = User(
            id = userId, name = "User", email = "user@example.com",
            avatar = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334)
        )
        val book = Book(
            id = UUID.randomUUID(),
            title = "New Book",
            author = "Author",
            price = 25.0,
            description = "Book Description",
            coverImage = "https:www.image.com",
            createdAt = Instant.ofEpochMilli(234453334),
            user = user
        )

        given(userRepository.findById(userId)).willReturn(Optional.of(user))
        given(bookRepository.save(Mockito.any(Book::class.java))).willReturn(book)

        val result = bookService.createBook(book, userId)

        assertNotNull(result)
        assertEquals(book.title, result.title)
        assertEquals(book.user?.id, result.user?.id)
    }

    @Test
    fun `should update book`() {
        val id = UUID.randomUUID()
        val book =
            Book(
                id = id, title = "Old Title", author = "Old Author", price = 30.0, description = "Book Description",
                coverImage = "https:www.image.com",
                createdAt = Instant.ofEpochMilli(234453334), user = null
            )
        val updatedBookDetails =
            Book(
                id = id, title = "New Title", author = "New Author", price = 35.0, description = "Book Description",
                coverImage = "https:www.image.com",
                createdAt = Instant.ofEpochMilli(234453334), user = null
            )

        given(bookRepository.findById(id)).willReturn(Optional.of(book))
        given(bookRepository.save(Mockito.any(Book::class.java))).willReturn(updatedBookDetails)

        val result = bookService.updateBook(id, updatedBookDetails)

        assertNotNull(result)
        assertEquals(updatedBookDetails.title, result.title)
        assertEquals(updatedBookDetails.author, result.author)
    }

    @Test
    fun `should delete book`() {
        val id = UUID.randomUUID()
        Mockito.doNothing().`when`(bookRepository).deleteById(id)

        bookService.deleteBook(id)

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(id)
    }
}