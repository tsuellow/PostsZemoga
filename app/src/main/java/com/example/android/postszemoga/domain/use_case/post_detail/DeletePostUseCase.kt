package com.example.android.postszemoga.domain.use_case.post_detail

import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class DeletePostUseCase (
    private val repository: PostsRepository
) {
    suspend operator fun invoke(post:Post){
        return repository.deletePost(post)
    }
}