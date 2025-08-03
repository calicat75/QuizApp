package com.example.dailyquiz.data.di

import com.example.dailyquiz.data.remote.QuizAPI
import com.example.dailyquiz.data.repository.QuizRepositoryImpl
import com.example.dailyquiz.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideQuizApi(): QuizAPI {
        return Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(quizAPI: QuizAPI): QuizRepository {
        return QuizRepositoryImpl(quizAPI)
    }
}