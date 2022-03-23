package com.example.android.todotask.models


import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "task")

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title: String,
    var Date: String,
    var Status:String,
    val Description:String,
    //ForeinKey
    val user_id:Int


)