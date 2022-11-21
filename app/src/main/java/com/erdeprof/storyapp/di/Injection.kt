package com.erdeprof.storyapp.di

import android.content.Context
import com.erdeprof.storyapp.dashboard.data.StoryRepository
import com.erdeprof.storyapp.database.StoryDatabase
import com.erdeprof.storyapp.network.NetworkConfig

object Injection {
    fun provideRepository(/*context: Context,*/ token: String): StoryRepository {
        // val database = StoryDatabase.getDatabase(context)
        val apiService = NetworkConfig.getService(token)
        return StoryRepository(apiService)
    }
}