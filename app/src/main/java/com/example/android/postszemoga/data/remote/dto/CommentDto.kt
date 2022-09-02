package com.example.android.postszemoga.data.remote.dto

import com.example.android.postszemoga.domain.model.Comment

data class CommentDto(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
) {
    fun toComment(): Comment {
        return Comment(body = body, email = email, id = id, name = name, postId = postId)
    }
}