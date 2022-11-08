package com.erdeprof.storyapp.dashboard.presenter

import com.erdeprof.storyapp.dashboard.data.Story

interface StoriesView {
    fun onSuccessStories (msg : String?, data : ArrayList<Story>?)
    fun onFailedStories (msg : String?)
}