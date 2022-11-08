package com.erdeprof.storyapp.dashboard.data

import com.google.gson.annotations.SerializedName

data class ResultStories(
    @field:SerializedName("listStory")
    val listStory: ArrayList<Story>? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("error")
    val error: Boolean? = null
)
