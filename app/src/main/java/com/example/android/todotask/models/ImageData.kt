package com.example.android.todotask.models

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageData")
data class ImageData(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val image: String

)