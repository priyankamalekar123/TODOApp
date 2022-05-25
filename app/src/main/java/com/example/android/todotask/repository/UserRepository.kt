package com.example.android.todotask.repository

import com.example.android.todotask.db.TODODatabase
import com.example.android.todotask.models.ImageData
import com.example.android.todotask.models.Subtask
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User

class UserRepository(private val todoDatabase: TODODatabase) {

    suspend fun addUser(user: User) {
        todoDatabase.userDao().addUser(user)
    }

    suspend fun getEmails(): List<String> {
        val Emails = todoDatabase.userDao().getEmail()
        return Emails

    }

    suspend fun getUserPassword(): List<String> {
        val userPassword = todoDatabase.userDao().getUserPassword()
        return userPassword
    }

    suspend fun getUser(email: String): User {
        val user = todoDatabase.userDao().getUser(email)
        return user
    }

    suspend fun addTask(task: Task) {
        todoDatabase.userDao().addTask(task)
    }

    suspend fun getAllTask(user_id: Int): List<Task> {
        return todoDatabase.userDao().getAllTask(user_id)
    }

    suspend fun getAllTaskwithTaskId(id: Int): Task {
        return todoDatabase.userDao().getAllTaskwithTaskId(id)
    }


    suspend fun deleteTask(task: Task) {
        todoDatabase.userDao().deleteTask(task)
    }

    suspend fun updateTask(task: Task) {
        todoDatabase.userDao().updateTask(task)
    }

    suspend fun getTask(title: String): List<Task> {
        return todoDatabase.userDao().getTask(title)
    }

    suspend fun getTaskStatus(status:String):List<Task>{
        var taskstatus = todoDatabase.userDao().getTaskStatus(status)
        return taskstatus
    }

    suspend fun updateStatus(status: String,id: Int){
        todoDatabase.userDao().updateStatus(status,id)
    }

    suspend fun addSubTask(subtask: Subtask){
        todoDatabase.userDao().addSubTask(subtask)
    }

    suspend fun getSubTask(task_id:Int):List<Subtask>{
        var subtask = todoDatabase.userDao().getSubTask(task_id)
        return subtask
    }

    suspend fun setCheckStatus(boolean: Boolean,id: Int){
        todoDatabase.userDao().setCheckStatus(boolean,id)
    }

    suspend fun addImage(image:ImageData){
        todoDatabase.userDao().addImage(image)
    }

    suspend fun getImage():List<String>{
        var images = todoDatabase.userDao().getImage()
        return images
    }

}