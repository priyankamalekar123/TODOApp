package com.example.android.todotask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subtask")
data class Subtask(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title:String,
    val is_checked:Boolean,

    //ForeinKey
    val task_id:Int
)
