package com.example.android.postszemoga.domain.use_case.posts_list

import com.example.android.postszemoga.domain.repository.PostsRepository

class DeleteAllPostsUseCase(
    private val repository: PostsRepository
) {

    suspend operator fun invoke(){
        repository.deleteAllPosts()
    }
}