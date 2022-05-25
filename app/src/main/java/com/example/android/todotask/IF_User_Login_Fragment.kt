package com.example.android.todotask

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_i_f__user__login_.*

class IF_User_Login_Fragment : Fragment(R.layout.fragment_i_f__user__login_) {

    lateinit var mFirebaseAnalytics:FirebaseAnalytics

    private val sharedPrefFile = "UserCredential"
   

    @SuppressLint("InvalidAnalyticsName")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.requireContext())


        go_to_dashboard.setOnClickListener {
            findNavController().navigate(R.id.action_IF_User_Login_Fragment_to_myTaskFragment)


        }
   // Custom Crashes for testing
        crash.setOnClickListener {
          // throw RuntimeException("Testing the crash")
            var bundle = Bundle()
            bundle.putString("firebase data","go_to_dashboard_button")
            bundle.putInt("integer",12)
            mFirebaseAnalytics.logEvent("button1",bundle)
        }
    }

}