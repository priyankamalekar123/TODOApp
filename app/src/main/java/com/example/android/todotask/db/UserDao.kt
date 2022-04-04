package com.example.android.todotask.db

import androidx.room.*
import com.example.android.todotask.models.Subtask
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

    @Query("SELECT * FROM task WHERE id = :id ")
    suspend fun getAllTaskwithTaskId(id: Int):Task

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task WHERE Title = :title")
    suspend fun getTask(title: String): List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskId(id:Int):List<Task>

    @Query("SELECT * FROM task WHERE Status= :status ")
    suspend fun getTaskStatus(status:String):List<Task>

    @Query("UPDATE task SET Status = :status WHERE id = :id")
    suspend fun updateStatus(status: String,id: Int)

    @Insert()
    suspend fun addSubTask(subtask: Subtask)

    @Query("SELECT * FROM subtask WHERE task_id = :task_id")
    suspend fun getSubTask(task_id:Int):List<Subtask>

    @Query("UPDATE subtask SET is_checked = :boolean_val WHERE id = :id")
    suspend fun setCheckStatus(boolean_val: Boolean,id: Int)


}