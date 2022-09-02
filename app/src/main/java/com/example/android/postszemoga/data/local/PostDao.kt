package com.example.android.postszemoga.data.local

import androidx.room.*
import com.example.android.postszemoga.domain.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM post ORDER BY isFavourite DESC")
    fun getPosts(): Flow<List<Post>>

    @Query("SELECT * FROM post where id=:postId")
    suspend fun getPostById(postId:Int):Post

    @Query("SELECT id FROM post where isFavourite=1")
    suspend fun getFavourites():List<Int>

    @Update
    suspend fun updatePost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts:List<Post>)

    @Delete
    suspend fun deletePost(post:Post)

    @Query("DELETE FROM post WHERE isFavourite=0")
    suspend fun deleteAll()
}