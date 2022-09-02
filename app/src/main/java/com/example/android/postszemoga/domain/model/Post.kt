package com.example.android.postszemoga.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val isFavourite: Boolean=false
)
