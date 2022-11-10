package com.erdeprof.storyapp.register.data
import com.google.gson.annotations.SerializedName

data class ResultRegister(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = null
)
