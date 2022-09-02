package com.example.android.postszemoga.domain.use_case.post_detail

import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Comment
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class GetCommentsUseCase (
    private val repository: PostsRepository
) {
    operator fun invoke(postId:Int): Flow<Resource<List<Comment>>> =
        repository.getCommentsById(postId)
}