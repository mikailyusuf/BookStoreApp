package com.mikail.bookStoreApp.feature.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/books")
@CrossOrigin
class BookController(@Autowired private val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): List<Book> = bookService.getAllBooks()

    @GetMapping("/user")
    fun getUserBooks(): List<Book> = bookService.getUserBooks()

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: UUID): Book = bookService.getBookById(id)

    @GetMapping("/search")
    fun searchBooks(@RequestParam keyword: String): List<Book> = bookService.searchBooks(keyword)

    @PostMapping
    fun createBook(
        @RequestPart("title") title: String,
        @RequestPart("author") author: String,
        @RequestPart("description") description: String,
        @RequestPart("price") price: String,
        @RequestPart("coverImage", required = false) coverImage: MultipartFile
    ): Book = bookService.createBook(
        CreateBookRequest(
            title = title,
            author = author,
            description = description,
            price = price.toDouble(),
            coverImage = coverImage
        )
    )

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: UUID, @RequestBody book: Book): Book = bookService.updateBook(id, book)

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: UUID) = bookService.deleteBook(id)

    @GetMapping("/user/{id}")
    fun getBookUserBooks(@PathVariable id: UUID): List<Book> = bookService.getBooksByUserId(id)
}