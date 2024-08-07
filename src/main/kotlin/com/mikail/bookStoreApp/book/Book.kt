package com.mikail.bookStoreApp.book

import com.fasterxml.jackson.annotation.JsonBackReference
import com.mikail.bookStoreApp.user.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val author: String,
    @Column(length = 1000)
    val description: String,
    val price: Double,
    val coverImage:String,
    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User? = null
)
