package com.example.android.postszemoga.domain.use_case.post_detail

class PostDetailUseCases (
    val getPostUseCase: GetPostUseCase,
    val deletePostUseCase: DeletePostUseCase,
    val getAuthorUseCase: GetAuthorUseCase,
    val getCommentsUseCase: GetCommentsUseCase,
    val updatePostUseCase: UpdatePostUseCase
    )