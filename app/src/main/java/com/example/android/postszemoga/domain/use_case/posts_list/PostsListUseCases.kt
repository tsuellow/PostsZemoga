package com.example.android.postszemoga.domain.use_case.posts_list

import com.example.android.postszemoga.domain.use_case.post_detail.GetPostUseCase

class PostsListUseCases(
    val deleteAllPostsUseCase: DeleteAllPostsUseCase,
    val getPostsUseCase: GetPostsUseCase,
    val refreshPostsUseCase: RefreshPostsUseCase
)