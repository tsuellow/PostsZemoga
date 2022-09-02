package com.example.android.postszemoga.common

import com.example.android.postszemoga.domain.model.Post

object Util {

    /**
     * dummy method created to showcase unit testing abilities
     */
    fun isPostTitleLong(post: Post):Boolean{
        return post.title.length>50 || post.title.split(" ").size>10
    }
}