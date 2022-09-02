package com.example.android.postszemoga.domain.repository

import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Comment
import com.example.android.postszemoga.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getPosts(): Flow<List<Post>>

    fun refreshPosts():Flow<Resource<Unit>>

    suspend fun deletePost(post:Post)

    suspend fun deleteAllPosts()

    suspend fun updatePost(post:Post)

    fun getCommentsById(postId:Int):Flow<Resource<List<Comment>>>

    fun getAuthor(userId:Int): Flow<Resource<Author>>

    suspend fun getPost(postId:Int):Post

}