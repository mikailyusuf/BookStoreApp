package com.mikail.bookStoreApp.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : JpaRepository<Book, UUID>{
    fun findByUserId(userId: UUID): List<Book>
}