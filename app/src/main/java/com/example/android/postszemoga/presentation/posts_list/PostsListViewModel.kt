package com.example.android.postszemoga.presentation.posts_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.use_case.posts_list.PostsListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsListViewModel @Inject constructor(
    private val postsListUseCases: PostsListUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var isFirstTime:Boolean=true

    private val _refreshState: MutableState<RefreshState> =
        mutableStateOf<RefreshState>(RefreshState.Loading)
    val refreshState: State<RefreshState> = _refreshState


    private val _listPost: MutableState<List<Post>> = mutableStateOf<List<Post>>(emptyList())
    val listPost: State<List<Post>> = _listPost

    init {
        getLocalPostsList()
    }


    private fun getLocalPostsList() {
        postsListUseCases.getPostsUseCase().onEach { changedList ->
            _listPost.value = changedList

            if (isFirstTime){
                _refreshState.value=RefreshState.Refresh
                if (changedList.isEmpty()){
                    refreshPostsFromRemoteSource()
                }
                isFirstTime=false
            }
        }.launchIn(viewModelScope)
    }

    private fun refreshPostsFromRemoteSource() {
        viewModelScope.launch {
            postsListUseCases.refreshPostsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _refreshState.value = RefreshState.Success
                        delay(500)
                        _refreshState.value = RefreshState.Refresh
                    }
                    is Resource.Error -> {
                        _refreshState.value = RefreshState.Failed
                        delay(500)
                        _refreshState.value = RefreshState.Refresh
                    }
                    else -> {
                        _refreshState.value = RefreshState.Loading
                    }
                }

            }
        }

    }

    private fun deleteAllPosts() {
        CoroutineScope(Dispatchers.IO).launch {
            postsListUseCases.deleteAllPostsUseCase()
        }
    }

    fun onEvent(event: PostListEvent) {
        when (event) {
            is PostListEvent.Refresh -> {
                refreshPostsFromRemoteSource()
            }
            is PostListEvent.DeleteAll -> {
                deleteAllPosts()
            }
            is PostListEvent.PostPressed -> {
                //navigate to new screen
            }
        }
    }
}

sealed class PostListEvent() {
    object Refresh : PostListEvent()
    object DeleteAll : PostListEvent()
    data class PostPressed(val postId: Int) : PostListEvent()
}