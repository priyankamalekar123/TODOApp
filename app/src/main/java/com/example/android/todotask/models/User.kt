package com.example.android.todotask.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password:String


)