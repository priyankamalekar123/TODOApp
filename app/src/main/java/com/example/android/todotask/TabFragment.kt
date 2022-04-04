package com.example.android.todotask

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tab.*
import java.text.FieldPosition
import javax.xml.xpath.XPathFactory.newInstance

class TabFragment : Fragment(R.layout.fragment_tab) {

    private val sharedPrefFile = "UserCredential"

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        tablayout.addTab(tablayout.newTab().setText("Overview"))
//        tablayout.addTab(tablayout.newTab().setText("Attachment & Record"))
//        tablayout.tabGravity = TabLayout.GRAVITY_FILL
//
//        val tabAdapter = TabAdapter(this.context!!,childFragmentManager,tablayout.tabCount)
//        viewpager.adapter = tabAdapter

  //creating instance of fragment
     var taskFragment = TaskFragment()

 //SharedPreference Instances
        val sharedPreferences: SharedPreferences =
            this.activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //getSharedPreference value
        val getemail = sharedPreferences.getString("email_key", null)
        val getpassword = sharedPreferences.getString("password_key", null)

        // get position and id
        val position1 = arguments!!.getInt("position")
        Log.d("position_is ", position1.toString())
        val id1 = arguments!!.getInt("ID")
        Log.d("iddddd_isssss", id.toString())


        // attach tablayout with viewpager
        tablayout.setupWithViewPager(viewpager)
        val tabAdapter = TabAdapter(childFragmentManager)

        tabAdapter.addFrag(TaskFragment.newInstance(position1,id1),"Overview")
        tabAdapter.addFrag(RecordFragment.newInstance(position1,id1),"Sub Task")

        // set adapter on viewpager
        viewpager.adapter = tabAdapter

        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        //viewpager.isHorizontalScrollBarEnabled = true

        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewpager.currentItem = tab!!.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

                 }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }
}




