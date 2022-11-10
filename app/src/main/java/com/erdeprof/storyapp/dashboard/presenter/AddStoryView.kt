package com.erdeprof.storyapp.dashboard.presenter

interface AddStoryView {
    fun onSuccessAddStory (msg : String?)
    fun onFailedAddStory (msg : String?)
}