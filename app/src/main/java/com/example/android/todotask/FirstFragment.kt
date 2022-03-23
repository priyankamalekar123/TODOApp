package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment(R.layout.fragment_first) {

    //SharedPreferences
    private val sharedPrefFile = "UserCredential"
    var getemail:String? = null
    var getpassword:String? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences = this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        //getSharedPreference value
         getemail = sharedPreferences.getString("email_key",null)
         getpassword = sharedPreferences.getString("password_key",null)

        Log.d("Email_11_value", getemail.toString())
        Log.d("Password_value", getpassword.toString())

        if (getemail != null && getpassword != null) {
            findNavController().navigate(R.id.action_firstFragment_to_IF_User_Login_Fragment)
        }

        signup_login.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_welcomeFragment)
        }
    }
}