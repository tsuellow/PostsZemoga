package com.example.android.postszemoga.di

import android.app.Application
import androidx.room.Room
import com.example.android.postszemoga.common.Constants
import com.example.android.postszemoga.data.local.PostDb
import com.example.android.postszemoga.data.remote.PostsApi
import com.example.android.postszemoga.data.repository.PostsRepositoryImpl
import com.example.android.postszemoga.domain.repository.PostsRepository
import com.example.android.postszemoga.domain.use_case.post_detail.*
import com.example.android.postszemoga.domain.use_case.posts_list.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostDb(app:Application):PostDb{
        return Room.databaseBuilder(
            app,
            PostDb::class.java,
            PostDb.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePostApi(app:Application):PostsApi{
        return Retrofit.Builder()
            .baseUrl(Constants.PLACEHOLDER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api:PostsApi,db:PostDb):PostsRepository{
        return PostsRepositoryImpl(api,db.postDao)
    }

    @Provides
    @Singleton
    fun providePostsListUseCases(repository: PostsRepository):PostsListUseCases{
        return PostsListUseCases(
            deleteAllPostsUseCase = DeleteAllPostsUseCase(repository),
            getPostsUseCase = GetPostsUseCase(repository),
            refreshPostsUseCase = RefreshPostsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(repository: PostsRepository):PostDetailUseCases{
        return PostDetailUseCases(
            getPostUseCase = GetPostUseCase(repository),
            deletePostUseCase = DeletePostUseCase(repository),
            getAuthorUseCase = GetAuthorUseCase(repository),
            getCommentsUseCase = GetCommentsUseCase(repository),
            updatePostUseCase = UpdatePostUseCase(repository)
        )
    }
}