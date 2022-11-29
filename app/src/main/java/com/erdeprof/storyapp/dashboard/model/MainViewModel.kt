package com.erdeprof.storyapp.dashboard.model

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.di.Injection
import com.erdeprof.storyapp.login.data.LoginRepository
import com.erdeprof.storyapp.login.data.ResultLogin

class MainViewModel : ViewModel() {
    private val _story = MutableLiveData<PagingData<Story>>()
    var story: LiveData<PagingData<Story>> = _story

    private val loginRepository = LoginRepository()

    private val _login = MutableLiveData<ResultLogin>()
    val login: LiveData<ResultLogin> = _login

    fun getStory(token: String) {
        story = Injection.provideRepository(token).getStory().cachedIn(viewModelScope)
    }

    fun getLogin(email: String, password: String) {
        loginRepository.login(email, password)
        val loginResult = loginRepository.getLogin()

        _login.value = ResultLogin(
            loginResult = loginResult,
            message = "success",
            error = false
        )
    }
}