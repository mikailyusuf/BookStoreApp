package com.mikail.bookStoreApp.config

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

@Component
class UserContext {

    fun getUserEmail(): String {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return if (principal is User) {
            principal.username
        } else {
            principal.toString()
        }
    }
}