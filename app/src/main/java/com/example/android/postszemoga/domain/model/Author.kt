package com.example.android.postszemoga.domain.model

import com.example.android.postszemoga.data.remote.dto.Company

data class Author(
    val company: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)
