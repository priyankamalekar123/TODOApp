package com.example.android.todotask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.fragment_full_screen_image.*

class FullScreenImageFragment : Fragment(R.layout.fragment_full_screen_image) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var image = arguments?.getString("image")
        //selectedImage.setImageResource(image!!)
       // selectedImage.setImageURI(imageis)

        var imageis = image?.toUri()

       selectedImage.setImageURI(imageis!!)

        //selectedImage.setImageResource(image.toInt())

    }
}