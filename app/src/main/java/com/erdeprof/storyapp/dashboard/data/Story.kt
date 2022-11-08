package com.erdeprof.storyapp.dashboard.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    var lon: String?,
    var lat: String?,
    var createdAt: String?,
    var photoUrl: String?,
    var description: String?,
    var name: String?,
    var id: String?
) : Parcelable

/*data class Story(
    @field:SerializedName("lon")
    val lon: String? = null,
    @field:SerializedName("lat")
    val lat: String? = null,
    @field:SerializedName("createdAt")
    val createdAt: String? = null,
    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,
    @field:SerializedName("description")
    val description: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("id")
    val id: String? = null
) : Parcelable*/
