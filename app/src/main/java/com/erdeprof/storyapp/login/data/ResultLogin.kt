package com.erdeprof.storyapp.login.data

import com.google.gson.annotations.SerializedName
data class ResultLogin(
    /*@field:SerializedName("staff")
    val staff: Staff? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("status")
    val status: Int? = null*/

    @field:SerializedName("error")
    val error: Boolean? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("loginResult")
    val loginResult: User? = null
)
