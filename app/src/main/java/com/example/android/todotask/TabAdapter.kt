package com.example.android.todotask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

//class TabAdapter(var context: Context,fm:FragmentManager,var total_tab:Int):FragmentPagerAdapter(fm) {
//
//    override fun getItem(position: Int): Fragment {
//        return when(position){
//            0 -> {
//                return TaskFragment()
//            }
//            1 ->{
//                return RecordFragment()
//            }
//            else -> getItem(position)
//        }
//        }
//    override fun getCount(): Int {
//        return total_tab
//    }
//}

class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var  mFragmentList:ArrayList<Fragment> = ArrayList<Fragment>()
    var  mFragmentTitleList:ArrayList<String> = ArrayList<String>()
    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList.get(position)
    }

    fun addFrag(fragment: Fragment, title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)

    }
}
