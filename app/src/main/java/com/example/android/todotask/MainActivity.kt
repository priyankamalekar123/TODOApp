package com.example.android.todotask

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    lateinit var appBarConfiguration:AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //navController = navHostFragment.findNavController()
        navController = navHostFragment.navController
       // appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController/*,appBarConfiguration*/)

       var actionBar: ActionBar? = getSupportActionBar()
//        //actionBar!!.setTitle("TODO Tasks")
//
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//
        var colorDrawable:ColorDrawable = ColorDrawable(Color.parseColor("#FA675C"))
        actionBar!!.setBackgroundDrawable(colorDrawable)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(/*appBarConfiguration*/) || super.onSupportNavigateUp()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.bottom_menu,menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.frag1 -> findNavController().navigate(R.id.action_loginFragment_to_myTaskFragment)
//
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
