package com.erdeprof.storyapp.di

import com.erdeprof.storyapp.dashboard.data.StoryRepository
import com.erdeprof.storyapp.network.NetworkConfig

object Injection {
    fun provideRepository(token: String): StoryRepository {
        val apiService = NetworkConfig.getService(token)
        return StoryRepository(apiService)
    }
}