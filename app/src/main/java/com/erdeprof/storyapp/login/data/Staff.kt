package com.erdeprof.storyapp.login.data

import com.google.gson.annotations.SerializedName
data class Staff(
    @field:SerializedName("staff_name")
    val staffName: String? = null,
    @field:SerializedName("staff_id")
    val staffId: String? = null,
    @field:SerializedName("staff_hp")
    val staffHp: String? = null,
    @field:SerializedName("staff_alamat")
    val staffAlamat: String? = null,
    @field:SerializedName("staff_password")
    val staffPassword: String? = null,
    @field:SerializedName("staff_email")
    val staffEmail: String? = null
)
