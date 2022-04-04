package com.example.android.todotask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class detailViewModel(private val userRepository: UserRepository) : ViewModel() {

    var tasks = MutableLiveData<List<Task>>()
    var dropdown_item = MutableLiveData<String>()
    val user1 = MutableLiveData<User>()
    var task1 = MutableLiveData<List<Task>>()




    fun getAllTask(user_id: Int) {
        GlobalScope.launch {
            var task = userRepository.getAllTask(user_id)
            tasks.postValue(task)

        }
    }

    fun deleteTask(task: Task) {
        GlobalScope.launch {
            userRepository.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        GlobalScope.launch {
            userRepository.updateTask(task)
        }
    }

    fun getTask(title: String) {
        GlobalScope.launch {
            var task2 = userRepository.getTask(title)
            task1.postValue(task2)
        }

    }


    fun getUser(email: String) {
        GlobalScope.launch {
            var user = userRepository.getUser(email)
            user1.postValue(user)
        }
    }

}