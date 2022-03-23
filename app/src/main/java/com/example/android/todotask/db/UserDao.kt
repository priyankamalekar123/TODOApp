package com.example.android.todotask.db

import androidx.room.*
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User

@Dao

interface UserDao {


    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT email FROM Users")
    suspend fun getEmail(): List<String>

    @Query("SELECT password FROM Users")
    suspend fun getUserPassword(): List<String>

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUser(email: String): User


//    @Query("SELECT * FROM Users")
//    suspend fun getUserData():List<String>

    @Insert
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task WHERE user_id = :user_id ")
    suspend fun getAllTask(user_id: Int): List<Task>

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task WHERE Title = :title")
    suspend fun getTask(title: String): List<Task>


}