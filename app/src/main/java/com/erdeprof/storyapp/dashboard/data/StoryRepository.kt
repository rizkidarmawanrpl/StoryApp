package com.erdeprof.storyapp.dashboard.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.erdeprof.storyapp.database.StoryDatabase
import com.erdeprof.storyapp.network.NetworkService

class StoryRepository(/*private val storyDatabase: StoryDatabase,*/ private val apiService: NetworkService) {
    fun getStory(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 2
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}