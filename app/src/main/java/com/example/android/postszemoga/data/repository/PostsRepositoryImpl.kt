package com.example.android.postszemoga.data.repository

import com.example.android.postszemoga.common.Resource
import com.example.android.postszemoga.data.local.PostDao
import com.example.android.postszemoga.data.remote.PostsApi
import com.example.android.postszemoga.domain.model.Author
import com.example.android.postszemoga.domain.model.Comment
import com.example.android.postszemoga.domain.model.Post
import com.example.android.postszemoga.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApi,
    private val dao: PostDao
) : PostsRepository {
    override fun getPosts(): Flow<List<Post>> {
        return dao.getPosts()
        //return api.getPosts().map { it.toPost() }
    }

    //pull new posts from api, flag favourites and insert into db
    override fun refreshPosts(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val freshPosts = api.getPosts().map { it.toPost() }
            val favourites = dao.getFavourites().toTypedArray()
            val updatedPosts = freshPosts.map { it.copy(isFavourite = it.id in favourites) }
            dao.insertPosts(updatedPosts)
            emit(Resource.Success(Unit))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message() ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "server unreachable"))
        }
    }

    override suspend fun deletePost(post: Post) {
        dao.deletePost(post)
    }

    override suspend fun deleteAllPosts() {
        dao.deleteAll()
    }

    override suspend fun updatePost(post: Post) {
        dao.updatePost(post)
    }

    override fun getCommentsById(postId: Int): Flow<Resource<List<Comment>>> = flow {
        try {
            emit(Resource.Loading())
            val comments = api.getCommentsById(postId).map { it.toComment() }
            emit(Resource.Success(comments))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message() ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "server unreachable"))
        }
    }

    override fun getAuthor(userId: Int): Flow<Resource<Author>> = flow {
        try {
            emit(Resource.Loading())
            val author = api.getAuthorData(userId).toAuthor()
            emit(Resource.Success(author))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message() ?: "unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "server unreachable"))
        }
    }

    override suspend fun getPost(postId: Int): Post {
        return dao.getPostById(postId)
    }
}