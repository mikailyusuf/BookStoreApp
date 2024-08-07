package com.mikail.bookStoreApp.user

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.mikail.bookStoreApp.book.Book
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
    val avatar: String?,
    var password: String,
    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val books: List<Book>? = mutableListOf()
)
