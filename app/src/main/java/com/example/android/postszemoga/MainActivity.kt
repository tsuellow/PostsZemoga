package com.example.android.postszemoga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.android.postszemoga.presentation.post_detail.components.PostDetailScreen
import com.example.android.postszemoga.presentation.posts_list.components.PostsListScreen
import com.example.android.postszemoga.presentation.ui.theme.PostsZemogaTheme
import com.example.android.postszemoga.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostsZemogaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    //Greeting(name = "Theo")
                    val navController= rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.PostsListScreen.route){
                        composable(route = Screen.PostsListScreen.route){
                            PostsListScreen(navController=navController)
                        }
                        composable(route = Screen.PostDetailScreen.route+"/{postId}"){
                            PostDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PostsZemogaTheme {
        Greeting("Android")
    }
}