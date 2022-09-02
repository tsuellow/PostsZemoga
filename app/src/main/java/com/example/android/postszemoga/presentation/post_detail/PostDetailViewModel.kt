package com.example.android.postszemoga.presentation.post_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Comment
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.use_case.post_detail.PostDetailUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates


@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postDetailUseCases: PostDetailUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var postId = -1

    private val _post: MutableState<Post> =
        mutableStateOf<Post>(Post(id = 0, title = "loading...", body = "loading...", userId = 0))
    val post: State<Post> = _post

    private val _resourceAuthor: MutableState<Resource<Author>> =
        mutableStateOf<Resource<Author>>(Resource.Loading())
    val resourceAuthor: State<Resource<Author>> = _resourceAuthor

    private val _resourceListComment: MutableState<Resource<List<Comment>>> =
        mutableStateOf<Resource<List<Comment>>>(Resource.Loading())
    val resourceListComment: State<Resource<List<Comment>>> = _resourceListComment

    init {
        savedStateHandle.get<String>("postId")?.let { id ->
            postId = id.toInt()
            viewModelScope.launch {
                // use 2 launch scopes because getPosts and getAuthor have to be
                // executed sequentially but getComments can run asynchronously
                launch {
                    getPost(postId)
                    getAuthor(_post.value.userId)
                }
                launch { getComments(postId) }
            }
        }
    }

    private suspend fun getPost(postId: Int) {
        _post.value = postDetailUseCases.getPostUseCase(postId)
    }

    private suspend fun getAuthor(userId: Int) {
        postDetailUseCases.getAuthorUseCase(userId).collect { res ->
            _resourceAuthor.value = res
        }
    }

    private suspend fun getComments(postId: Int) {
        postDetailUseCases.getCommentsUseCase(postId).collect { res ->
            _resourceListComment.value = res
        }
    }

    private fun toggleFavourite() {
        _post.value = _post.value.copy(isFavourite = !_post.value.isFavourite)
        CoroutineScope(Dispatchers.IO).launch {
            postDetailUseCases.updatePostUseCase(_post.value)
        }
    }

    private fun deletePost() {
        CoroutineScope(Dispatchers.IO).launch {
            postDetailUseCases.deletePostUseCase(_post.value)
        }
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.ToggleFavourite -> {
                toggleFavourite()
            }
            is PostDetailEvent.DeletePost -> {
                deletePost()
            }
        }
    }

}

sealed class PostDetailEvent() {
    object ToggleFavourite : PostDetailEvent()
    object DeletePost : PostDetailEvent()
}