package com.mikail.bookStoreApp.feature.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
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
    fun createBook(@RequestBody book: Book): Book = bookService.createBook(book)

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: UUID, @RequestBody book: Book): Book = bookService.updateBook(id, book)

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: UUID) = bookService.deleteBook(id)

    @GetMapping("/user/{id}")
    fun getBookUserBooks(@PathVariable id: UUID): List<Book> = bookService.getBooksByUserId(id)
}