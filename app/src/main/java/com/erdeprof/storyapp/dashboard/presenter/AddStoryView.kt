package com.erdeprof.storyapp.dashboard.presenter

import com.erdeprof.storyapp.login.data.User

interface AddStoryView {
    fun onSuccessAddStory (msg : String?)
    fun onFailedAddStory (msg : String?)
}