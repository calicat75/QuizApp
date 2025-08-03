package com.example.dailyquiz.domain.di

import com.example.dailyquiz.domain.repository.QuizRepository
import com.example.dailyquiz.domain.usecase.GetQuestionsUseCase
import com.example.dailyquiz.domain.usecase.GetHistoryUseCase
import com.example.dailyquiz.domain.usecase.SaveResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetQuestionsUseCase(repository: QuizRepository): GetQuestionsUseCase {
        return GetQuestionsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetHistoryUseCase(repository: QuizRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveResultUseCase(repository: QuizRepository): SaveResultUseCase {
        return SaveResultUseCase(repository)
    }
}