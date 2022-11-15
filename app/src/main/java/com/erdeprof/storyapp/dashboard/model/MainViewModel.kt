package com.erdeprof.storyapp.dashboard.model

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.data.StoryRepository
import com.erdeprof.storyapp.di.Injection
import kotlinx.coroutines.launch

class MainViewModel(/*private val storyRepository: StoryRepository*/) : ViewModel() {
    // private val _story = MutableLiveData<List<Story>>()
    // var story: LiveData<List<Story>> = _story

//    val quote: LiveData<PagingData<Story>> =
//        storyRepository.getStory().cachedIn(viewModelScope)

    private val _story = MutableLiveData<PagingData<Story>>()
    // var story: LiveData<PagingData<Story>> = storyRepository.getStory().cachedIn(viewModelScope) // _story
    var story: LiveData<PagingData<Story>> = _story

    fun getStory(context: Context, token: String) {
        story = Injection.provideRepository(context, token).getStory().cachedIn(viewModelScope)
    }

//    fun getStory() {
//        viewModelScope.launch {
//            _story.postValue(storyRepository.getStory())
//        }
//    }
}

//class ViewModelFactory(private val context: Context, private val token: String) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return MainViewModel(Injection.provideRepository(context, token)) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}