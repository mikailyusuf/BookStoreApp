package com.mikail.bookStoreApp.feature.book

import com.mikail.bookStoreApp.config.UserContext
import com.mikail.bookStoreApp.feature.user.UserRepository
import com.mikail.bookStoreApp.utils.generateUniqueFileName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


@Service
class BookService(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val userRepository: UserRepository,
    private val userContext: UserContext
) {
    private val uploadDir: Path = Paths.get("uploads")

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

    fun createBook(request: CreateBookRequest): Book {
        val user = userRepository.findByEmail(userContext.getUserEmail())
        val imagePath = request.coverImage.let {
            val uniqueFileName = generateUniqueFileName(it.originalFilename!!)
            val filePath = uploadDir.resolve(uniqueFileName)

            Files.copy(it.inputStream, filePath)
            uniqueFileName
        }

        return bookRepository.save(
            Book(
                title = request.title,
                description = request.description,
                author = request.author,
                price = request.price, user = user, coverImage = "images/${imagePath}"
            )
        )
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