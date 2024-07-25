package com.mikail.BookStoreApp.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/books")
@CrossOrigin
class BookController(@Autowired private val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): List<Book> = bookService.getAllBooks()

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: UUID): Book = bookService.getBookById(id)

    @PostMapping
    fun createBook(@RequestBody book: Book, @RequestParam userId: UUID): Book = bookService.createBook(book, userId)

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: UUID, @RequestBody book: Book): Book = bookService.updateBook(id, book)

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: UUID) = bookService.deleteBook(id)
}