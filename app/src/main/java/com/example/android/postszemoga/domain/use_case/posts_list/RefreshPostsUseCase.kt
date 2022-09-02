package com.example.android.postszemoga.domain.use_case.posts_list

import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow

class RefreshPostsUseCase(
    private val repository: PostsRepository
) {
    operator fun invoke(): Flow<Resource<Unit>> {
        return repository.refreshPosts()
    }
}