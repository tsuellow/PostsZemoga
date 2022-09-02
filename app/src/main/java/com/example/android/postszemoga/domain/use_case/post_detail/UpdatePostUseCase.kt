package com.example.android.postszemoga.domain.use_case.post_detail

import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.repository.PostsRepository

class UpdatePostUseCase (
    private val repository: PostsRepository
) {
    suspend operator fun invoke(post:Post){
        repository.updatePost(post)
    }

}