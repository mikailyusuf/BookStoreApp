package com.mikail.BookStoreApp.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, UUID>