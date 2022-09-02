package com.example.android.postszemoga.domain.use_case.post_detail

import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetPostUseCase (
    private val repository: PostsRepository
) {
    suspend operator fun invoke(postId:Int): Post {
        return repository.getPost(postId)
    }

}