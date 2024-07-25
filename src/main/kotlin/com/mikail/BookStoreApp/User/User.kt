package com.mikail.BookStoreApp.User

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.mikail.BookStoreApp.book.Book
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*


@Entity
@Table(name = "app_user")
@EntityListeners(AuditingEntityListener::class)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val email: String,
    val avatar:String?,
    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val books: List<Book>? = mutableListOf()
)

