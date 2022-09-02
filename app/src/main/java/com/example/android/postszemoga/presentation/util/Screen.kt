package com.example.android.postszemoga.presentation.util

sealed class Screen(val route:String){
    object PostsListScreen:Screen(route = "postsList"){
        fun withId(deleteId:Int):String{
            return "$route/$deleteId"
        }
    }
    object PostDetailScreen:Screen(route = "postDetail"){
        fun withId(postId:Int):String{
            return "$route/$postId"
        }
    }
}
