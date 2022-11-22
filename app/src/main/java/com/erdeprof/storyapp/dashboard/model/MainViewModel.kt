package com.erdeprof.storyapp.dashboard.model

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.di.Injection

class MainViewModel : ViewModel() {
    private val _story = MutableLiveData<PagingData<Story>>()
    var story: LiveData<PagingData<Story>> = _story

    fun getStory(token: String) {
        story = Injection.provideRepository(token).getStory().cachedIn(viewModelScope)
    }
}