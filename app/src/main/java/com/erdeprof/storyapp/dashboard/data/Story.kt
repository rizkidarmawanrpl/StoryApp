package com.erdeprof.storyapp.dashboard.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "story")
@Parcelize
data class Story(
    var lon: String?,
    var lat: String?,
    var createdAt: String?,
    var photoUrl: String?,
    var description: String?,
    var name: String?,

    @PrimaryKey
    var id: String?
) : Parcelable