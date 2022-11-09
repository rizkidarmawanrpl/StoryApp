package com.erdeprof.storyapp.dashboard.data

import com.google.gson.annotations.SerializedName

data class ResultAddStory(
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("error")
    val error: Boolean? = null
)
