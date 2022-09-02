package com.example.android.postszemoga.presentation.posts_list

sealed class RefreshState(){
    object Loading:RefreshState()
    object Refresh:RefreshState()
    object Success:RefreshState()
    object Failed:RefreshState()
}
