package com.mikail.bookStoreApp.feature.book

import org.springframework.web.multipart.MultipartFile

data class CreateBookRequest(
    val title: String, val author: String, val price: Double,
    val description: String, val coverImage: MultipartFile
)
