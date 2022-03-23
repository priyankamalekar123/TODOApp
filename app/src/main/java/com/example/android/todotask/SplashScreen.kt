package com.example.android.todotask

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import java.time.OffsetDateTime

class SplashScreen : AppCompatActivity() {

    //SharedPreferences
    private val sharedPrefFile = "UserCredential"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var image = findViewById<ImageView>(R.id.splash_image)

        image.alpha = 0f
        image.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}