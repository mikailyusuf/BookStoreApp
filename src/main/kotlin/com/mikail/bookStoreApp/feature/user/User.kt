package com.mikail.bookStoreApp.feature.user

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.mikail.bookStoreApp.feature.book.Book
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.*


@Entity
@Table(name = "app_user", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
@EntityListeners(AuditingEntityListener::class)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(nullable = false) @NotEmpty val name: String,
    @Column(nullable = false) @Email @NotEmpty val email: String,
    @Column(nullable = false) @NotEmpty val password: String,
    val avatar: String?,
    @CreatedDate
    @Column(updatable = false)
    val createdAt: Instant = Instant.now(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val books: List<Book>? = mutableListOf()
)
