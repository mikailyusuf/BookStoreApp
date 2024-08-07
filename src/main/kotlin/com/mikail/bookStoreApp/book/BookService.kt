package com.mikail.bookStoreApp.book

import com.mikail.bookStoreApp.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class BookService(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val userRepository: UserRepository
) {

    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun getBooksByUserId(userId: UUID): List<Book> {
        return bookRepository.findByUserId(userId)
    }

    fun getBookById(id: UUID): Book = bookRepository.findById(id).orElseThrow { Exception("Book not found") }

    fun createBook(book: Book, userId: UUID): Book {
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
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