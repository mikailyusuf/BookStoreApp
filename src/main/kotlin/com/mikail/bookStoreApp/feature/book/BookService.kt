package com.mikail.bookStoreApp.feature.book

import com.mikail.bookStoreApp.config.UserContext
import com.mikail.bookStoreApp.feature.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class BookService(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val userRepository: UserRepository,
    private val userContext: UserContext
) {

    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun getBooksByUserId(userId: UUID): List<Book> {
        return bookRepository.findByUserId(userId)
    }

    fun searchBooks(keyword: String): List<Book> = bookRepository.findByTitleOrAuthor(keyword)

    fun getBookById(id: UUID): Book = bookRepository.findById(id).orElseThrow { Exception("Book not found") }

    fun getUserBooks(): List<Book> {
        val user = userRepository.findByEmail(userContext.getUserEmail())
        return if (user != null) {
            bookRepository.findByUserId(user.id)
        } else {
            emptyList()
        }
    }

    fun createBook(book: Book): Book {
        val user = userRepository.findByEmail(userContext.getUserEmail())
        return bookRepository.save(book.copy(user = user))
    }

    fun updateBook(id: UUID, bookDetails: Book): Book {
        val book = getBookById(id)
        return bookRepository.save(
            book.copy(
                title = bookDetails.title,
                author = bookDetails.author,
                description = bookDetails.description,
                price = bookDetails.price,
                coverImage = bookDetails.coverImage
            )
        )
    }

    fun deleteBook(id: UUID) = bookRepository.deleteById(id)

}