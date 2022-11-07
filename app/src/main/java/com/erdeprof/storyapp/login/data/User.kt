package com.erdeprof.storyapp.login.data

import com.google.gson.annotations.SerializedName
data class User(
    @field:SerializedName("userId")
    val userId: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("token")
    val token: String? = null
)
