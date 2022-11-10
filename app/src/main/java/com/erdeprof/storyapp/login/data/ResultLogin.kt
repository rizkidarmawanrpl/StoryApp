package com.erdeprof.storyapp.login.data

import com.google.gson.annotations.SerializedName
data class ResultLogin(
    @field:SerializedName("loginResult")
    val loginResult: User? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("error")
    val error: Boolean? = null
)
