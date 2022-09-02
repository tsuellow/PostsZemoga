package com.example.android.postszemoga.data.remote.dto

import com.example.android.postszemoga.domain.model.Author

data class AuthorDto(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
) {
    fun toAuthor(): Author {
        return Author(
            company = company.name,
            email = email,
            id = id,
            name = name,
            phone = phone,
            username = username,
            website = website
        )
    }
}