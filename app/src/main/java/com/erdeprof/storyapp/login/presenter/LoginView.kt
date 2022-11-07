package com.erdeprof.storyapp.login.presenter

// import com.erdeprof.storyapp.login.data.Staff
import com.erdeprof.storyapp.login.data.User

interface LoginView {
    fun onSuccessLogin (msg : String?, data : User?)
    fun onFailedLogin (msg : String?)
}