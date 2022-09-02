package com.example.android.postszemoga.common

import com.example.android.postszemoga.domain.model.Post
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilTest{

    /**
     * I implemented this unit tests as dummy examples only to show that I am familiar with unit
     * testing. Since I am caching the posts in room all my business logic ended up in the dao.
     * Testing my dao methods would have required implementation tests so I decided to showcase
     * unit tests with a simple example
     */

    val post= Post(id = 0, title = "sample", body = "sampleBody", userId = 1)
    @Test
    fun `over a thirty letters returns true`(){
        val testPost=post.copy(title = "fdjkjdsafjladjsfklajsdfjksaljdlsajdaksjdaljsdqwertyu")
        val result=Util.isPostTitleLong(testPost)
        assertThat(result).isTrue()
    }

    @Test
    fun `over a ten words returns true`(){
        val testPost=post.copy(title = "que alegria mas alegre ma me mi mo mu me me")
        val result=Util.isPostTitleLong(testPost)
        assertThat(result).isTrue()
    }

    @Test
    fun `short title returns false`(){
        val testPost=post.copy(title = "que alegria mas alegre")
        val result=Util.isPostTitleLong(testPost)
        assertThat(result).isFalse()
    }
}