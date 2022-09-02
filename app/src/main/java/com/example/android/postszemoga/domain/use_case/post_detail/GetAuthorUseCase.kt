package com.example.android.postszemoga.domain.use_case.post_detail

import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetAuthorUseCase(
    private val repository: PostsRepository
) {
    operator fun invoke(userId:Int): Flow<Resource<Author>> =
         repository.getAuthor(userId)
}
