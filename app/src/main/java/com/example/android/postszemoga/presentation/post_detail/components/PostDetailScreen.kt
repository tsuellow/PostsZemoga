package com.example.android.postszemoga.presentation.post_detail.components

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Comment
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.presentation.common_components.DeleteFab
import com.example.android.postszemoga.presentation.common_components.MyTopBar
import com.example.android.postszemoga.presentation.post_detail.PostDetailEvent
import com.example.android.postszemoga.presentation.post_detail.PostDetailViewModel
import com.example.android.postszemoga.presentation.posts_list.components.PostItem
import com.example.android.postszemoga.presentation.util.Screen

@Composable
fun PostDetailScreen(
    viewModel: PostDetailViewModel = hiltViewModel(),
    navController: NavController
) {

    Scaffold(
        topBar = {
            MyTopBar(title = "Post Detail", iconButton = {
                FavouriteButton(
                    post = viewModel.post.value,
                    onClick = { viewModel.onEvent(PostDetailEvent.ToggleFavourite) })
            })
        },
        floatingActionButton = {
            DeleteFab(onClick = {
                viewModel.onEvent(PostDetailEvent.DeletePost)
                navController.popBackStack(route = Screen.PostsListScreen.route, inclusive = false,saveState = true)
            })
        }
    ) { padding ->

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item { PostData(post = viewModel.post.value) }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item { AuthorData(resource = viewModel.resourceAuthor.value) }
            item { Spacer(modifier = Modifier.height(12.dp)) }

            //Comments Section
            item {
                TitleLoadable(
                    title = "Comments",
                    status = viewModel.resourceListComment.value as Resource<Any>
                )
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            if (viewModel.resourceListComment.value is Resource.Success) {
                val comments = viewModel.resourceListComment.value.data ?: emptyList()
                items(comments) { comment ->
                    CommentItem(comment = comment)
                    Divider(Modifier.padding(horizontal = 8.dp))
                }

            }
            item { Spacer(modifier = Modifier.height(72.dp)) }
        }
    }
}

@Composable
fun PostData(post: Post) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleLoadable(title = "Description", status = Resource.Success(null))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = post.title,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(modifier = Modifier.padding(start = 16.dp), text = post.body)
    }
}


@Composable
fun TitleLoadable(title: String, status: Resource<Any>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            when (status) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp).size(20.dp), strokeWidth = 2.dp)
                }
                is Resource.Error -> {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "error",
                        tint = MaterialTheme.colors.error
                    )
                }
                else -> {

                }
            }
        }
    }

}


@Composable
fun AuthorData(resource: Resource<Author>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleLoadable(title = "Author", status = resource as Resource<Any>)
        Spacer(modifier = Modifier.width(12.dp))
        Attribute(
            title = "Name",
            value = if (resource is Resource.Success) resource.data!!.name else "..."
        )
        Attribute(
            title = "Email",
            value = if (resource is Resource.Success) resource.data!!.email else "..."
        )
        Attribute(
            title = "Company",
            value = if (resource is Resource.Success) resource.data!!.company else "..."
        )
        Attribute(
            title = "Website",
            value = if (resource is Resource.Success) resource.data!!.website else "..."
        )
    }
}

@Composable
fun Attribute(title: String, value: String = "...") {
    Row(
        modifier = Modifier.padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = "$title:",
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = value,
        )
    }
}


//Deprecated because functionality was lacking
@Composable
fun CommentsData(resource: Resource<List<Comment>>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleLoadable(title = "Comments", status = resource as Resource<Any>)
        Spacer(modifier = Modifier.height(8.dp))
        if (resource is Resource.Success) {
            val comments = resource.data ?: emptyList()
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(comments) { comment ->
                    CommentItem(comment = comment)
                    Divider(Modifier.padding(horizontal = 8.dp))
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .padding(10.dp),
    ) {

        Text(
            text = comment.name,
            modifier = Modifier.wrapContentWidth(align = Alignment.Start, unbounded = false),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = comment.email,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.caption
            )
        }
    }
}



@Composable
fun FavouriteButton(post: Post, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        if (post.isFavourite) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "not favourite",
                tint = Color.Yellow
            )
        } else {
            Icon(
                imageVector = Icons.Filled.StarOutline,
                contentDescription = "favourite",

                )
        }
    }
}