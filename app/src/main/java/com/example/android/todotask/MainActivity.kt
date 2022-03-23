package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var actionBar: ActionBar? = getSupportActionBar()

        var colorDrawable:ColorDrawable = ColorDrawable(Color.parseColor("#144C0B"))

        actionBar!!.setBackgroundDrawable(colorDrawable)
    }
}