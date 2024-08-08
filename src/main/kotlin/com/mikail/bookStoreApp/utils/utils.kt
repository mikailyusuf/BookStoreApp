package com.mikail.bookStoreApp.utils

import java.util.*

fun generateUniqueFileName(originalFileName: String): String {
    val extension = originalFileName.substringAfterLast('.', "")
    val uniqueName = UUID.randomUUID().toString()
    return if (extension.isNotEmpty()) "$uniqueName.$extension" else uniqueName
}
