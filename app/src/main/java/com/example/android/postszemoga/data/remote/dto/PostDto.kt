package com.example.android.postszemoga.data.remote.dto

import com.example.android.postszemoga.domain.model.Post

data class PostDto(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
) {
    fun toPost(): Post {
        return Post(body = body, id = id, title = title, userId = userId)
    }
}