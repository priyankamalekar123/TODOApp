package com.example.android.todotask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {

    private lateinit var navController1: NavController
    lateinit var mFirebaseAnalytics:FirebaseAnalytics


    lateinit var appBarConfiguration:AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //navController = navHostFragment.findNavController()
        navController1 = navHostFragment.navController
       // appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController1/*,appBarConfiguration*/)

       var actionBar: ActionBar? = getSupportActionBar()
//        //actionBar!!.setTitle("TODO Tasks")
//
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//
        var colorDrawable:ColorDrawable = ColorDrawable(Color.parseColor("#FA675C"))
        actionBar!!.setBackgroundDrawable(colorDrawable)

//   Firebase initiated
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

// firebase analytics event1
//     var bundle = Bundle().apply {
//         this.putString("name is","Priyanka")
//         this.putInt("Age is",21)
//     }
//        mFirebaseAnalytics.setDefaultEventParameters(bundle)

//  firebase analytics suggested event
    var bundle1 = Bundle()
        bundle1.putInt(FirebaseAnalytics.Param.ITEM_ID,1)
        bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME,"Priyanka")
        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE,"image")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle1)
//        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true)
//        mFirebaseAnalytics.setSessionTimeoutDuration(1000000)

//      firebase analytics custom parameter
        val parameters = Bundle().apply {
            this.putString("level_name", "Caverns01")
            this.putInt("level_difficulty", 4)
        }
        mFirebaseAnalytics.setDefaultEventParameters(parameters)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController1.navigateUp(/*appBarConfiguration*/) || super.onSupportNavigateUp()
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
