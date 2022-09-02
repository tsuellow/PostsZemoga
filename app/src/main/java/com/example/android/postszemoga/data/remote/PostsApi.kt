package com.example.android.postszemoga.data.remote

import com.example.android.postszemoga.data.remote.dto.AuthorDto
import com.example.android.postszemoga.data.remote.dto.CommentDto
import com.example.android.postszemoga.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("/posts")
    suspend fun getPosts():List<PostDto>

    @GET("/posts/{postId}/comments")
    suspend fun getCommentsById(@Path("postId") postId:Int):List<CommentDto>

    @GET("/users/{userId}")
    suspend fun getAuthorData(@Path("userId") userId:Int):AuthorDto
}