package com.example.android.postszemoga.domain.use_case.posts_list

import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val repository: PostsRepository
) {
    operator fun invoke(): Flow<List<Post>>{
        return repository.getPosts()
    }
}