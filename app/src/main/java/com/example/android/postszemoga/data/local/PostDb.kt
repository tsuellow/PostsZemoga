package com.example.android.postszemoga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.postszemoga.domain.model.Post

@Database(
    entities = [Post::class],
    version = 1
)
abstract class PostDb: RoomDatabase() {
    abstract val postDao:PostDao

    companion object{
        const val DB_NAME="posts_db"
    }
}