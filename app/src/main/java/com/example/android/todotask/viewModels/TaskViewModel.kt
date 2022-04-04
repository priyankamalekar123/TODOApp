package com.example.android.todotask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskViewModel(private val userRepository: UserRepository) : ViewModel() {

    val user1 = MutableLiveData<User>()
    var task1 = MutableLiveData<List<Task>>()
    val tasks3 = MutableLiveData<Task>()

    fun getUser(email:String){
        GlobalScope.launch {
            var user = userRepository.getUser(email)
            user1.postValue(user)

        }
    }

    fun getAllTask(id:Int){
        GlobalScope.launch {
            var task = userRepository.getAllTask(id)
            task1.postValue(task)

        }

    }
    fun getAllTaskwithTaskId(id: Int) {
        GlobalScope.launch {
            val tasks2 = userRepository.getAllTaskwithTaskId(id)
            tasks3.postValue(tasks2)

        }
    }
    fun updateTask(task: Task){
        GlobalScope.launch {
            userRepository.updateTask(task)
        }
    }

    fun updateStatus(status: String,id: Int){
        GlobalScope.launch {
            userRepository.updateStatus(status,id)
        }

    }


}