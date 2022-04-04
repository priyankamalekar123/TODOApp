package com.example.android.todotask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.Subtask
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecordViewModel(private val userRepository: UserRepository):ViewModel() {
    var subtask1 = MutableLiveData<List<Subtask>>()
    fun addSubTask(subtask: Subtask){
        GlobalScope.launch {
            userRepository.addSubTask(subtask)
        }
    }

    fun getSubTask(task_id:Int){
        GlobalScope.launch {
          val subtask = userRepository.getSubTask(task_id)
            subtask1.postValue(subtask)
        }
    }

    fun setCheckStatus(boolean: Boolean,id: Int){
        GlobalScope.launch {
            userRepository.setCheckStatus(boolean,id)
        }
    }

}