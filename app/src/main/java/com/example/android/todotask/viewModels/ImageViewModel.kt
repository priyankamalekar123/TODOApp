package com.example.android.todotask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.todotask.models.ImageData
import com.example.android.todotask.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageViewModel(private val userRepository: UserRepository):ViewModel() {

    var images = MutableLiveData<List<String>>()

    fun addImage(image:ImageData){
        GlobalScope.launch {
            userRepository.addImage(image)
        }
    }

    fun getImage(){
        GlobalScope.launch {
            var image1 = userRepository.getImage()
            images.postValue(image1)
        }

    }
}