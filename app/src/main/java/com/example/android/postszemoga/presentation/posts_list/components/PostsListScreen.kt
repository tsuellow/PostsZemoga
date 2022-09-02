package com.example.android.postszemoga.presentation.posts_list.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.presentation.common_components.DeleteFab
import com.example.android.postszemoga.presentation.common_components.MyTopBar
import com.example.android.postszemoga.presentation.posts_list.PostListEvent
import com.example.android.postszemoga.presentation.posts_list.PostsListViewModel
import com.example.android.postszemoga.presentation.posts_list.RefreshState
import com.example.android.postszemoga.presentation.ui.theme.PostsZemogaTheme
import com.example.android.postszemoga.presentation.util.Screen

@Composable
fun PostsListScreen(
    viewModel: PostsListViewModel = hiltViewModel(),
    navController: NavController
) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { scaffoldState.snackbarHostState },
        topBar = {
            MyTopBar(title = "Posts", iconButton = {
                RefreshButton(
                    refreshState = viewModel.refreshState.value,
                    onClick = { viewModel.onEvent(PostListEvent.Refresh) })
            })
        },
        floatingActionButton = {
            DeleteFab(
                onClick = { viewModel.onEvent(PostListEvent.DeleteAll) },
                icon = Icons.Filled.DeleteForever
            )
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (viewModel.listPost.value.isEmpty()) {
                if (viewModel.refreshState.value is RefreshState.Loading || viewModel.refreshState.value is RefreshState.Success) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        text = "loading posts..."
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        text = "No posts to show!\nPress REFRESH to retrieve posts"
                    )
                }

            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.listPost.value) { post ->
                        PostItem(
                            post = post,
                            onItemClick = {
                                navController.navigate(route = Screen.PostDetailScreen.withId(post.id))
                            })
                        Divider(Modifier.padding(horizontal = 8.dp))
                    }
                }

            }

        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onItemClick: (Int) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 64.dp)
        .clickable { onItemClick(post.id) }
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text(text = "${post.id}. ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                text = post.title,
                fontSize = 16.sp,
                modifier = Modifier.wrapContentWidth(align = Alignment.Start, unbounded = false)
            )
        }
        Spacer(modifier = Modifier.padding(12.dp))
        Row() {
            if (post.isFavourite) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "favourite",
                    tint = Color.Yellow
                )
            }

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = "go",
                tint = Color.LightGray
            )
        }

    }

}


@Composable
fun RefreshButton(refreshState: RefreshState, onClick: () -> Unit) {
    IconButton(onClick = { if (refreshState is RefreshState.Refresh) onClick() }) {
        when (refreshState) {
            is RefreshState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colors.onPrimary,
                    strokeWidth = 2.dp
                )
            }
            is RefreshState.Success -> {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "check")
            }
            is RefreshState.Refresh -> {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "refresh")
            }
            is RefreshState.Failed -> {
                Icon(imageVector = Icons.Filled.Error, contentDescription = "error")
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PostsZemogaTheme {
        Column() {
            MyTopBar(
                title = "Post List",
                iconButton = { RefreshButton(refreshState = RefreshState.Refresh, onClick = {}) }
            )

            PostItem(post = Post(0, "test", "bla bla bla", 0, true), onItemClick = { })

        }

    }
}