package com.mikail.bookStoreApp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class BookStoreAppApplication

fun main(args: Array<String>) {
	runApplication<BookStoreAppApplication>(*args)
}
