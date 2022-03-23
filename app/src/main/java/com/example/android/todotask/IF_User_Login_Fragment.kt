package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_i_f__user__login_.*

class IF_User_Login_Fragment : Fragment(R.layout.fragment_i_f__user__login_) {

    private val sharedPrefFile = "UserCredential"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        go_to_dashboard.setOnClickListener {
            findNavController().navigate(R.id.action_IF_User_Login_Fragment_to_myTaskFragment)
        }
    }

}