package com.example.android.todotask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.Task
import com.example.android.todotask.models.User
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class myTaskViewModel(private val userRepository: UserRepository) : ViewModel() {

    val tasks = MutableLiveData<List<Task>>()
    val user1 = MutableLiveData<User>()
    fun getAllTask(user_id: Int) {
        GlobalScope.launch {
            val tasks1 = userRepository.getAllTask(user_id)
            tasks.postValue(tasks1)

        }
    }

    fun getUser(email: String) {
        GlobalScope.launch {
            var user = userRepository.getUser(email)
            user1.postValue(user)


        }
    }


}