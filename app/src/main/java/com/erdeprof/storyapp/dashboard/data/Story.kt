package com.erdeprof.storyapp.dashboard.data

import android.os.Parcelable
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